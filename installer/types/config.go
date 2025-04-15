package types

import (
	"gopkg.in/yaml.v2"
	"io/ioutil"
	"os"
	"path"
)

type Config struct {
	ServerType   string `yaml:"server_type"`
	ServerName   string `yaml:"server_name"`
	MainServer   string `yaml:"main_server"`
	Password     string `yaml:"password"`
	InternalKey  string `yaml:"internal_key"`
	Branch       string `yaml:"branch"`
	DataDir      string `yaml:"data_dir"`
	CloudProvider string `yaml:"cloud_provider"`
	Region       string `yaml:"region"`
	VPCID        string `yaml:"vpc_id"`
	SubnetID     string `yaml:"subnet_id"`
	InstanceType string `yaml:"instance_type"`
	VolumeSize   int    `yaml:"volume_size"`
}

type StackConfig struct {
	Cert         string
	LocksDir     string
	LogstashConfig string
}

var configPath = path.Join("/root", "utmstack.yml")

func (c *Config) Get() {
	config, err := os.ReadFile(configPath)
	if err != nil {
		return
	}

	_ = yaml.Unmarshal(config, c)
}

func (c *Config) Set() error {
	config, err := yaml.Marshal(c)
	if err != nil {
		return err
	}

	err = os.WriteFile(configPath, config, 0644)
	if err != nil {
		return err
	}

	return nil
}

func (c *Config) LoadConfig(file string) error {
	data, err := ioutil.ReadFile(file)
	if err != nil {
		return err
	}

	return yaml.Unmarshal(data, c)
}

func (c *Config) SaveConfig(file string) error {
	data, err := yaml.Marshal(c)
	if err != nil {
		return err
	}

	return ioutil.WriteFile(file, data, 0644)
}

func (s *StackConfig) Populate(c *Config) error {
	s.Cert = path.Join(c.DataDir, "cert")
	s.LocksDir = path.Join(c.DataDir, "locks")
	s.LogstashConfig = path.Join(c.DataDir, "logstash", "config")

	return nil
}
