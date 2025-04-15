#!/bin/bash

# UTMStack Installer
# Version: 1.0.0

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m'

# Function to print status messages
print_status() {
    echo -e "${GREEN}[*] $1${NC}"
}

print_error() {
    echo -e "${RED}[!] $1${NC}"
}

print_warning() {
    echo -e "${YELLOW}[!] $1${NC}"
}

# Check if running as root
if [ "$EUID" -ne 0 ]; then 
    print_error "Please run as root"
    exit 1
fi

# System requirements
MIN_RAM=4096  # 4GB
MIN_CPU=2
MIN_DISK=20   # 20GB

# Check system requirements
print_status "Checking system requirements..."

# Check RAM
TOTAL_RAM=$(free -m | awk '/^Mem:/{print $2}')
if [ "$TOTAL_RAM" -lt "$MIN_RAM" ]; then
    print_warning "System has less than 4GB RAM. Performance may be affected."
fi

# Check CPU cores
CPU_CORES=$(nproc)
if [ "$CPU_CORES" -lt "$MIN_CPU" ]; then
    print_warning "System has less than 2 CPU cores. Performance may be affected."
fi

# Check disk space
FREE_DISK=$(df -BG / | awk 'NR==2 {print $4}' | sed 's/G//')
if [ "$FREE_DISK" -lt "$MIN_DISK" ]; then
    print_error "Insufficient disk space. Minimum 20GB required."
    exit 1
fi

# Update system
print_status "Updating system packages..."
apt-get update && apt-get upgrade -y

# Install required packages
print_status "Installing required packages..."
apt-get install -y \
    curl \
    wget \
    git \
    docker.io \
    docker-compose \
    postgresql \
    nginx \
    certbot \
    python3-certbot-nginx \
    openjdk-11-jdk \
    maven \
    nodejs \
    npm \
    golang-go

# Start and enable Docker
print_status "Configuring Docker..."
systemctl start docker
systemctl enable docker

# Create UTMStack directory structure
print_status "Creating directory structure..."
mkdir -p /opt/utmstack/{data,logs,config}
mkdir -p /opt/utmstack/data/{elasticsearch,postgresql}
mkdir -p /opt/utmstack/logs/{nginx,backend,frontend}

# Clone UTMStack repository
print_status "Cloning UTMStack repository..."
git clone https://github.com/utmstack/UTMStack.git /opt/utmstack/source

# Build backend
print_status "Building backend..."
cd /opt/utmstack/source/backend
mvn clean package -DskipTests

# Build frontend
print_status "Building frontend..."
cd /opt/utmstack/source/frontend
npm install
npm run build --prod

# Configure PostgreSQL
print_status "Configuring PostgreSQL..."
sudo -u postgres psql -c "CREATE DATABASE utmstack;"
sudo -u postgres psql -c "CREATE USER utmstack WITH PASSWORD 'utmstack';"
sudo -u postgres psql -c "GRANT ALL PRIVILEGES ON DATABASE utmstack TO utmstack;"

# Configure Nginx
print_status "Configuring Nginx..."
cat > /etc/nginx/sites-available/utmstack << EOF
server {
    listen 80;
    server_name _;

    location / {
        root /opt/utmstack/source/frontend/dist;
        try_files \$uri \$uri/ /index.html;
    }

    location /api {
        proxy_pass http://localhost:8080;
        proxy_set_header Host \$host;
        proxy_set_header X-Real-IP \$remote_addr;
    }

    location /ws {
        proxy_pass http://localhost:8080;
        proxy_http_version 1.1;
        proxy_set_header Upgrade \$http_upgrade;
        proxy_set_header Connection "upgrade";
    }
}
EOF

ln -s /etc/nginx/sites-available/utmstack /etc/nginx/sites-enabled/
rm -f /etc/nginx/sites-enabled/default
systemctl restart nginx

# Create systemd service for backend
print_status "Creating systemd service for backend..."
cat > /etc/systemd/system/utmstack-backend.service << EOF
[Unit]
Description=UTMStack Backend Service
After=network.target postgresql.service

[Service]
Type=simple
User=root
WorkingDirectory=/opt/utmstack/source/backend
ExecStart=/usr/bin/java -jar target/utmstack-*.war
Restart=always
Environment=SPRING_PROFILES_ACTIVE=prod
Environment=SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/utmstack
Environment=SPRING_DATASOURCE_USERNAME=utmstack
Environment=SPRING_DATASOURCE_PASSWORD=utmstack

[Install]
WantedBy=multi-user.target
EOF

# Create systemd service for correlation engine
print_status "Creating systemd service for correlation engine..."
cat > /etc/systemd/system/utmstack-correlation.service << EOF
[Unit]
Description=UTMStack Correlation Engine
After=network.target

[Service]
Type=simple
User=root
WorkingDirectory=/opt/utmstack/source/correlation
ExecStart=/usr/bin/go run main.go
Restart=always

[Install]
WantedBy=multi-user.target
EOF

# Enable and start services
print_status "Starting services..."
systemctl daemon-reload
systemctl enable utmstack-backend
systemctl enable utmstack-correlation
systemctl start utmstack-backend
systemctl start utmstack-correlation

# Create admin user
print_status "Creating admin user..."
cd /opt/utmstack/source/backend
java -jar target/utmstack-*.war --spring.profiles.active=prod --spring.datasource.url=jdbc:postgresql://localhost:5432/utmstack --spring.datasource.username=utmstack --spring.datasource.password=utmstack --create-admin-user

# Print installation summary
print_status "Installation completed successfully!"
echo "============================================="
echo "UTMStack has been installed successfully!"
echo "Access the web interface at: http://your-server-ip"
echo "Default admin credentials:"
echo "Username: admin"
echo "Password: (check /opt/utmstack/config/admin-password.txt)"
echo "============================================="

# Save admin password
cat > /opt/utmstack/config/admin-password.txt << EOF
Please check the backend logs for the generated admin password.
You can find it by running: journalctl -u utmstack-backend | grep "Generated admin password"
EOF

print_status "Please secure your installation by:"
print_status "1. Setting up SSL certificates using certbot"
print_status "2. Changing the default admin password"
print_status "3. Configuring firewall rules"
print_status "4. Setting up proper backup procedures" 