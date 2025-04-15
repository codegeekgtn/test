package utils

import (
	"fmt"
	"os"
	"os/exec"
	"path/filepath"
	"runtime"
	"strings"
)

func IsRoot() bool {
	return os.Geteuid() == 0
}

func CheckRequirements() error {
	// Check CPU
	if err := CheckCPU(2); err != nil {
		return err
	}

	// Check RAM
	if err := CheckRAM(4096); err != nil {
		return err
	}

	// Check Disk
	if err := CheckDisk(30); err != nil {
		return err
	}

	// Check OS
	if runtime.GOOS != "linux" {
		return fmt.Errorf("unsupported operating system: %s", runtime.GOOS)
	}

	return nil
}

func CheckCPU(minCores int) error {
	cores := runtime.NumCPU()
	if cores < minCores {
		return fmt.Errorf("insufficient CPU cores: %d (minimum %d required)", cores, minCores)
	}
	return nil
}

func CheckRAM(minRAM int) error {
	memInfo, err := os.ReadFile("/proc/meminfo")
	if err != nil {
		return err
	}

	lines := strings.Split(string(memInfo), "\n")
	for _, line := range lines {
		if strings.HasPrefix(line, "MemTotal:") {
			fields := strings.Fields(line)
			if len(fields) >= 2 {
				ramKB := strings.TrimSuffix(fields[1], "kB")
				ramMB := strings.TrimSuffix(ramKB, "kB") / 1024
				if ramMB < minRAM {
					return fmt.Errorf("insufficient RAM: %dMB (minimum %dMB required)", ramMB, minRAM)
				}
				return nil
			}
		}
	}
	return fmt.Errorf("could not determine RAM size")
}

func CheckDisk(minGB int) error {
	cmd := exec.Command("df", "-BG", "/")
	output, err := cmd.Output()
	if err != nil {
		return err
	}

	lines := strings.Split(string(output), "\n")
	if len(lines) >= 2 {
		fields := strings.Fields(lines[1])
		if len(fields) >= 4 {
			available := strings.TrimSuffix(fields[3], "G")
			if available < minGB {
				return fmt.Errorf("insufficient disk space: %sGB (minimum %dGB required)", available, minGB)
			}
			return nil
		}
	}
	return fmt.Errorf("could not determine disk space")
}

func CreateDirs(config *types.Config) error {
	dirs := []string{
		config.DataDir,
		filepath.Join(config.DataDir, "data"),
		filepath.Join(config.DataDir, "logs"),
		filepath.Join(config.DataDir, "config"),
		filepath.Join(config.DataDir, "data", "elasticsearch"),
		filepath.Join(config.DataDir, "data", "postgresql"),
		filepath.Join(config.DataDir, "logs", "nginx"),
		filepath.Join(config.DataDir, "logs", "backend"),
		filepath.Join(config.DataDir, "logs", "frontend"),
	}

	for _, dir := range dirs {
		if err := os.MkdirAll(dir, 0755); err != nil {
			return fmt.Errorf("failed to create directory %s: %v", dir, err)
		}
	}

	return nil
}

func RunCmd(name string, arg ...string) error {
	cmd := exec.Command(name, arg...)
	cmd.Stdout = os.Stdout
	cmd.Stderr = os.Stderr
	return cmd.Run()
} 