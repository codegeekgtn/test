package main

import (
	"fmt"
	"io/ioutil"
	"os"
	"path/filepath"
	"strings"
	"time"

	"github.com/utmstack/UTMStack/installer/types"
	"github.com/utmstack/UTMStack/installer/utils"
)

func InstallDocker() error {
	fmt.Println("Installing Docker...")

	// Install Docker
	if err := utils.RunCmd("apt-get", "update"); err != nil {
		return err
	}

	if err := utils.RunCmd("apt-get", "install", "-y", "docker.io", "docker-compose"); err != nil {
		return err
	}

	// Start and enable Docker
	if err := utils.RunCmd("systemctl", "start", "docker"); err != nil {
		return err
	}

	if err := utils.RunCmd("systemctl", "enable", "docker"); err != nil {
		return err
	}

	return nil
}

func InitSwarm(mainIP string) error {
	fmt.Println("Initializing Docker Swarm...")

	// Initialize swarm
	if err := utils.RunCmd("docker", "swarm", "init", "--advertise-addr", mainIP); err != nil {
		return err
	}

	return nil
}

func StackUP(c *types.Config, stack *types.StackConfig) error {
	fmt.Println("Starting UTMStack services...")

	// Create docker-compose file
	composeFile := filepath.Join(c.DataDir, "docker-compose.yml")
	if err := createDockerCompose(composeFile, c, stack); err != nil {
		return err
	}

	// Deploy stack
	if err := utils.RunCmd("docker", "stack", "deploy", "-c", composeFile, "utmstack"); err != nil {
		return err
	}

	return nil
}

func createDockerCompose(file string, c *types.Config, stack *types.StackConfig) error {
	compose := `version: '3.8'

services:
  backend:
    image: utmstack/backend:${BRANCH}
    deploy:
      replicas: 1
      restart_policy:
        condition: on-failure
    environment:
      - SPRING_PROFILES_ACTIVE=prod
      - SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/utmstack
      - SPRING_DATASOURCE_USERNAME=utmstack
      - SPRING_DATASOURCE_PASSWORD=utmstack
    volumes:
      - ${DATA_DIR}/logs/backend:/logs
    networks:
      - utmstack

  frontend:
    image: utmstack/frontend:${BRANCH}
    deploy:
      replicas: 1
      restart_policy:
        condition: on-failure
    volumes:
      - ${DATA_DIR}/logs/frontend:/logs
    networks:
      - utmstack

  postgres:
    image: postgres:13
    deploy:
      replicas: 1
      restart_policy:
        condition: on-failure
    environment:
      - POSTGRES_USER=utmstack
      - POSTGRES_PASSWORD=utmstack
      - POSTGRES_DB=utmstack
    volumes:
      - ${DATA_DIR}/data/postgresql:/var/lib/postgresql/data
      - ${DATA_DIR}/logs/postgresql:/var/log/postgresql
    networks:
      - utmstack

  elasticsearch:
    image: docker.elastic.co/elasticsearch/elasticsearch:7.12.1
    deploy:
      replicas: 1
      restart_policy:
        condition: on-failure
    environment:
      - discovery.type=single-node
      - "ES_JAVA_OPTS=-Xms512m -Xmx512m"
    volumes:
      - ${DATA_DIR}/data/elasticsearch:/usr/share/elasticsearch/data
    networks:
      - utmstack

networks:
  utmstack:
    driver: overlay
`

	// Replace variables
	compose = strings.ReplaceAll(compose, "${BRANCH}", c.Branch)
	compose = strings.ReplaceAll(compose, "${DATA_DIR}", c.DataDir)

	// Write to file
	return ioutil.WriteFile(file, []byte(compose), 0644)
}

func PostInstallation() error {
	time.Sleep(3 * time.Minute)

	fmt.Println("Securing ports 9200, 5432 and 10000")

	if err := utils.RunCmd("docker", "service", "update", "--publish-rm", "9200", "utmstack_node1"); err != nil {
		return err
	}

	if err := utils.RunCmd("docker", "service", "update", "--publish-rm", "5432", "utmstack_postgres"); err != nil {
		return err
	}

	if err := utils.RunCmd("docker", "service", "update", "--publish-rm", "10000", "utmstack_correlation"); err != nil {
		return err
	}

	fmt.Println("Securing ports 9200, 5432 and 10000 [OK]")

	fmt.Println("Restarting Stack")

	time.Sleep(60 * time.Second)

	if err := utils.RunCmd("systemctl", "restart", "docker"); err != nil {
		return err
	}

	if err := Backend(); err != nil {
		return err
	}

	fmt.Println("Restarting Stack [OK]")

	fmt.Println("Cleaning up Docker system")

	if err := utils.RunCmd("docker", "system", "prune", "-f"); err != nil {
		return err
	}

	fmt.Println("Cleaning up Docker system [OK]")

	return nil
}
