FROM mcr.microsoft.com/vscode/devcontainers/base:ubuntu-22.04

# Install dependencies and Oracle JDK 21
RUN apt-get update && \
    apt-get install -y wget curl git maven ca-certificates && \
    wget https://download.oracle.com/java/21/latest/jdk-21_linux-x64_bin.deb && \
    apt install -y ./jdk-21_linux-x64_bin.deb && \
    rm jdk-21_linux-x64_bin.deb && \
    rm -rf /var/lib/apt/lists/* && \
    # Create symbolic links to ensure path consistency
    mkdir -p /usr/lib/jvm && \
    ln -sf $(find /usr/lib/jvm -maxdepth 1 -type d -name "jdk-21*") /usr/lib/jvm/jdk-21-oracle

# Set JAVA_HOME and update PATH
ENV JAVA_HOME=/usr/lib/jvm/jdk-21.0.7-oracle-x64
ENV PATH=$JAVA_HOME/bin:$PATH

# Update certificates
RUN update-ca-certificates

# Set working directory
WORKDIR /workspace

# Expose necessary ports
EXPOSE 8080