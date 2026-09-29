#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define TAM 10000 // Tamanho máximo da string do cenário

char* init_cenario(char **cenarios, int k);
int tem_time(int a, int *times, int tamanho_time, int inicio_time);
int mesmo_time(int a, int b, int *times, int *tam_times, int *index_times, int qtd_times);
int enqueue(int a, int *queue, int *tam_queue, int tam_max_queue, int *times, int qtd_times, int *tam_times, int *index_times);
int dequeue(int *queue, int *tam_queue, int tam_max_queue, char *final_cenario);

int main() {
    int k = 0;              // Contador de cenários
    char **cenarios = NULL; // Array de cenários

    int *queue = NULL;                      // Fila
    int tam_queue = 0, tam_max_queue = 0;   // Tamanho atual e máximo da fila

    int qtd_times;             // Quantidade de times
    int *times = NULL;         // Array de times
    int tam_vetor_times = 0;   // Tamanho total do vetor de times
    int *tam_times = NULL;     // Tamanhos dos times
    int *index_times = NULL;   // Índices dos times

    while (1) {
        // -------------------------------------------------------------- Realoca espaço para novo cenário
        cenarios = (char **) realloc(cenarios, (k + 1) * sizeof(char *));
        if (cenarios == NULL) {
            printf("Erro: Alocação de memória\n");
            return 1;
        }

        char *final_cenario = init_cenario(cenarios, k); // Inicializa o cenário
        if (final_cenario == NULL) 
            return 1;

        // -------------------------------------------------------------- Leitura dos times por cenário
        scanf("%d", &qtd_times);
        if (qtd_times == 0) 
            break;

        tam_times = (int *) malloc(qtd_times * sizeof(int));    // Tamanhos dos times
        index_times = (int *) malloc(qtd_times * sizeof(int));  // Índices dos times
        if (tam_times == NULL || index_times == NULL) {
            printf("Erro: Alocação de memória\n");
            return 1;
        }

        tam_vetor_times = 0; // Tamanho total do vetor de times
        times = NULL; 

        for (int i = 0; i < qtd_times; i++) {
            scanf("%d", &tam_times[i]); // Leitura do tamanho do time
            index_times[i] = tam_vetor_times;

            times = (int *) realloc(times, (tam_vetor_times + tam_times[i]) * sizeof(int)); // Cada realloc aumenta o tamanho do vetor de times
            if (times == NULL) {
                printf("Erro: Alocação de memória\n");
                return 1;
            }
            // Leitura dos elementos do time
            for (int j = 0; j < tam_times[i]; j++) {
                scanf("%d", &times[tam_vetor_times + j]); // Leitura dos elementos do time
            }

            tam_vetor_times += tam_times[i]; // Atualiza o tamanho total do vetor de times
        }

        // -------------------------------------------------------------- Inicialização da fila
        // Aloca espaço para a fila
        queue = (int *) malloc(tam_vetor_times * sizeof(int));
        if (queue == NULL) {
            printf("Erro: Alocação de memória\n");
            return 1;
        }
        // Inicializa a fila
        tam_queue = 0;
        tam_max_queue = tam_vetor_times;

        // -------------------------------------------------------------- Processamento dos comandos
        while (1) {
            char comando[20];
            scanf("%s", comando);

            if (strcmp(comando, "STOP") == 0) { // Finaliza o cenário
                k++;

                // printf("\n\n");
                // Libera memória alocada durante o cenário
                free(queue);
                queue = NULL;
                tam_queue = 0;

                free(times);
                times = NULL;
                tam_vetor_times = 0;

                free(tam_times);
                tam_times = NULL;

                free(index_times);
                index_times = NULL;

                break; // Sai do loop de comandos
            } else if (strcmp(comando, "DEQUEUE") == 0) { // Remove o primeiro elemento da fila
                if (dequeue(queue, &tam_queue, tam_max_queue, final_cenario) == -1) {
                    printf("Fila vazia\n");
                }
            } else if (strcmp(comando, "ENQUEUE") == 0) { // Adiciona um elemento à fila conforme lógica de implementação
                int a;
                scanf("%d", &a);
                int result = enqueue(a, queue, &tam_queue, tam_max_queue, times, qtd_times, tam_times, index_times);
                if (result == -1) {
                    printf("Elemento não pertence a nenhum time\n");
                } else if (result == -2) {
                    printf("Fila cheia\n");
                }
            }
        }
    }

    // --------------------------------------------------------------- Printa os cenários
    for (int i = 0; i < k; i++) {
        printf("%s\n", cenarios[i]);
        free(cenarios[i]);
    }
    free(cenarios);

    return 0;
}

char* init_cenario(char **cenarios, int k) {
    // Funcao para inicializar o cenário

    // Aloca espaço para o cenário
    cenarios[k] = (char *) malloc(TAM * sizeof(char));
    if (cenarios[k] == NULL) {
        printf("Erro: Alocação de memória\n");
        return NULL;
    }
    cenarios[k][0] = '\0';
    sprintf(cenarios[k], "Scenario #%d\n", k + 1);
    return cenarios[k] + strlen(cenarios[k]); // Retorna o ponteiro para o final da string do cenário
}

int tem_time(int a, int *times, int tamanho_time, int inicio_time) {
    // Funcao para verificar se o elemento a pertence a um dos times

    for (int i = 0; i < tamanho_time; i++) {
        if (a == times[inicio_time + i]) {
            return 1;
        }
    }
    return 0;
}

int mesmo_time(int a, int b, int *times, int *tam_times, int *index_times, int qtd_times) {
    // Funcao para verificar se os elementos a e b pertencem ao mesmo time

    for (int i = 0; i < qtd_times; i++) {
        if (tem_time(a, times, tam_times[i], index_times[i]) &&
            tem_time(b, times, tam_times[i], index_times[i])) {
            return 1;
        }
    }
    return 0;
}

int enqueue(int a, int *queue, int *tam_queue, int tam_max_queue, int *times, int qtd_times, int *tam_times, int *index_times) {
    int time_index = -1;
    for (int i = 0; i < qtd_times; i++) {
        if (tem_time(a, times, tam_times[i], index_times[i])) {
            time_index = i;
            break;
        }
    }

    if (time_index == -1)
        return -1;

    if (*tam_queue >= tam_max_queue)
        return -2;

    int pos;
    for (int i = 0; i < tam_max_queue; i++) {
        if (mesmo_time(a, queue[i], times, tam_times, index_times, qtd_times)) {
            // Encontrou alguém do mesmo time, colocar logo após ele
            pos = i;
            while (pos < tam_max_queue && mesmo_time(a, queue[pos + 1], times, tam_times, index_times, qtd_times)) {
                pos++;
            }
            break;
        } else {
            // Se não encontrou ninguém do mesmo time, colocar no final da fila
            pos = i;
        }
    }

    // Deslocar para esquerda os elementos a antes da posição encontrada
    for (int i = 1; i <= pos; i++) {
        queue[i - 1] = queue[i];
    }
    queue[pos] = a;

    /*
    printf("Fila após enqueue de %d: ", a);
    for (int i = 0; i < tam_max_queue; i++) {
        printf("[%d] ", queue[i]);
    }
    printf("\n");
    */

    (*tam_queue)++;

    return 0;
}

int dequeue(int *queue, int *tam_queue, int tam_max_queue, char *final_cenario) {
    if (*tam_queue == 0)
        return -1;

    int elemento = queue[tam_max_queue - *tam_queue]; // Pega o primeiro elemento da fila
    (*tam_queue)--;

    queue[tam_max_queue - *tam_queue - 1] = 0; // Zera o espaço da fila

    /*
    printf("Fila após dequeue de %d: ", elemento);
    for (int i = 0; i < tam_max_queue; i++) {
        printf("[%d] ", queue[i]);
    }
    printf("\n");
    */

    char buffer[20];
    sprintf(buffer, "%d\n", elemento);
    strcat(final_cenario, buffer);

    return elemento;
}
