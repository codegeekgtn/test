package main

import (
	"flag"
	"fmt"
	"os"
	"path/filepath"

	"github.com/utmstack/UTMStack/installer/types"
	"github.com/utmstack/UTMStack/installer/utils"
)

func main() {
	// Define command line flags
	configFile := flag.String("config", "config.yml", "Path to configuration file")
	update := flag.Bool("update", false, "Update existing installation")
	cloud := flag.Bool("cloud", false, "Install in cloud environment")
	flag.Parse()

	// Load configuration
	config, err := types.LoadConfig(*configFile)
	if err != nil {
		fmt.Printf("Error loading configuration: %v\n", err)
		os.Exit(1)
	}

	// Create necessary directories
	if err := utils.CreateDirs(config); err != nil {
		fmt.Printf("Error creating directories: %v\n", err)
		os.Exit(1)
	}

	// Check if running as root
	if !utils.IsRoot() {
		fmt.Println("This installer must be run as root")
		os.Exit(1)
	}

	// Check system requirements
	if err := utils.CheckRequirements(); err != nil {
		fmt.Printf("System requirements not met: %v\n", err)
		os.Exit(1)
	}

	// Choose installation mode
	if *cloud {
		if err := Cloud(config, *update); err != nil {
			fmt.Printf("Error in cloud installation: %v\n", err)
			os.Exit(1)
		}
	} else {
		if err := Master(config); err != nil {
			fmt.Printf("Error in local installation: %v\n", err)
			os.Exit(1)
		}
	}

	fmt.Println("Installation completed successfully!")
}
