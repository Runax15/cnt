#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <sys/types.h>
#include <sys/socket.h>

#define PORT 8000
#define BUFFER_SIZE 50

int main() {
    int serversocket;
    struct sockaddr_in serveraddress, clientaddress;
    socklen_t clientaddress_len = sizeof(clientaddress);
    char buffer[BUFFER_SIZE];

    // Creating socket
    if ((serversocket = socket(AF_INET, SOCK_DGRAM, 0)) < 0) {
        perror("Socket creation failed");
        return 1;
    }

    serveraddress.sin_family = AF_INET;
    serveraddress.sin_port = htons(PORT);
    serveraddress.sin_addr.s_addr = INADDR_ANY;

    // Bind
    if (bind(serversocket, (struct sockaddr *)&serveraddress, sizeof(serveraddress)) < 0) {
        perror("Bind failed");
        close(serversocket);
        return 1;
    }

    printf("Waiting for incoming connections...\n");

    // Receive message
    int recv_size;
    if ((recv_size = recvfrom(serversocket, buffer, BUFFER_SIZE - 1, 0, (struct sockaddr *)&clientaddress, &clientaddress_len)) < 0) {
        perror("Receive failed");
    } else {
        buffer[recv_size] = '\0';
        printf("Client message: %s\n", buffer);

        // Send response
        const char response[] = "Hello from server";
        sendto(serversocket, response, strlen(response), 0, (struct sockaddr *)&clientaddress, clientaddress_len);
    }

    // Cleanup
    close(serversocket);

    return 0;
}
