#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>

int main()
{
    int sock = socket(AF_INET, SOCK_STREAM, 0);

    struct sockaddr_in server_addr;
    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(1234);
    server_addr.sin_addr.s_addr = INADDR_ANY;

    connect(sock, (struct sockaddr *)&server_addr, sizeof(server_addr));
    printf("Conexión establecida\n");
    char message[100], server_resp[100];

    while (1)
    {
        char message[100] = {0};
        char server_resp[100] = {0};
        printf("Ingrese el mensaje: ");
        fgets(message, 100, stdin);
        fflush(stdin);

        if (send(sock, message, strlen(message), 0) < 0)
        {
            printf("Error al enviar\n");
            exit(1);
        }

        if (recv(sock, server_resp, 100, 0) < 0)
        {
            printf("Error al recibir\n");
            exit(1);
        }
        printf("Respuesta del servidor: %s\n", server_resp);
        if (strcmp(message, "Adios") == 0)
        {
            close(sock);
        }
    }
    close(sock);
    return 0;
}
