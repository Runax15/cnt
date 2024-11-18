// File: ftp_server.c

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>

#define PORT 8080
#define BUFFER_SIZE 1024

void send_file(FILE *fp, int socket);

int main() {
    int server_fd, new_socket;
    struct sockaddr_in address;
    int addrlen = sizeof(address);
    char buffer[BUFFER_SIZE] = {0};

    // Create socket file descriptor
    if ((server_fd = socket(AF_INET, SOCK_STREAM, 0)) == 0) {
        perror("Socket failed");
        exit(EXIT_FAILURE);
    }

    // Bind the socket to the port
    address.sin_family = AF_INET;
    address.sin_addr.s_addr = INADDR_ANY;
    address.sin_port = htons(PORT);

    if (bind(server_fd, (struct sockaddr *)&address, sizeof(address)) < 0) {
        perror("Bind failed");
        close(server_fd);
        exit(EXIT_FAILURE);
    }

    // Listen for incoming connections
    if (listen(server_fd, 3) < 0) {
        perror("Listen failed");
        close(server_fd);
        exit(EXIT_FAILURE);
    }

    printf("Server is listening on port %d\n", PORT);

    // Accept an incoming connection
    if ((new_socket = accept(server_fd, (struct sockaddr *)&address, (socklen_t*)&addrlen)) < 0) {
        perror("Accept failed");
        close(server_fd);
        exit(EXIT_FAILURE);
    }

    printf("Client connected\n");

    // Receive file name from client
    read(new_socket, buffer, BUFFER_SIZE);
    printf("Requested file: %s\n", buffer);

    FILE *fp = fopen(buffer, "r");
    if (fp == NULL) {
        perror("File not found");
        close(new_socket);
        close(server_fd);
        exit(EXIT_FAILURE);
    }

    // Send the file to the client
    send_file(fp, new_socket);
    printf("File sent successfully\n");

    // Close the file and socket
    fclose(fp);
    close(new_socket);
    close(server_fd);

    return 0;
}

void send_file(FILE *fp, int socket) {
    char data[BUFFER_SIZE] = {0};

    while (fgets(data, BUFFER_SIZE, fp) != NULL) {
        if (send(socket, data, sizeof(data), 0) == -1) {
            perror("Failed to send file");
            exit(EXIT_FAILURE);
        }
        memset(data, 0, BUFFER_SIZE);
    }
}
