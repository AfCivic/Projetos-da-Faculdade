#include <stdio.h>

typedef struct element{
    int id;
    int tm;
    struct element *next;
} element;

typedef struct{
    int id;
    int used;
} hashelement;

hashelement elist[1500000]; // hash table que vai conter todos os elementos (pelo menos 50% maior que o numero de elementos)

typedef struct internalteam{
    int id;
    element *first, *last;
    struct internalteam *nextonq;
} internalteam;

typedef struct{
    internalteam *first;
}teamqueue;

void botanotime (int team, element x, hashelement elist[]){
    int pos = (team * 1000) + (x.id % 1000) * 3 / 2; // hash utilizado pra colocar os elementos na tabela
    while (elist[pos].used != 0){
        pos ++; // se a posição estiver ocupada, passa pra proxima
    }
    elist[pos].id = x.id;
    elist[pos].used = 1;
}

void enqueue(int x, teamqueue *tq, int c){
    element *new = (element *)malloc(sizeof(element)); 
    new->id = x;
    new->tm = c;
    new->next = NULL;
    if (tq->first == NULL){
        internalteam *newteam =(internalteam *)malloc(sizeof(internalteam));
        newteam->id = c;
        newteam->first = new;
        newteam->last = new;
        newteam->nextonq = NULL;
        tq->first = newteam;
        return; // se não há nenhum time na fila, cria um
    } else{
        internalteam *atual = tq->first;
        while (atual->id != c){
            if (atual->nextonq == NULL){
                internalteam *newteam = (internalteam *)malloc(sizeof(internalteam));
                newteam->id = c;
                newteam->first = new;
                newteam->last = new;
                newteam->nextonq = NULL;
                atual->nextonq = newteam;
                return; // se não existe o time do cara, cria ele
            }
            atual = atual->nextonq;
        }
        atual->last->next = new;
        atual->last = new; // se acha o time do cara, bota ele no fimdo time
    }
    return;
}

void findteamenqueue (int x, teamqueue *tq, hashelement elist[], int n){
    for (int c = 1; c <= n; c++){
        int trgt = (c * 1000) + (x % 1000) * 3 / 2; //checa time por time se o objeto está nele
        while (elist[trgt].used != 0){
            if (elist[trgt].id == x){ 
                enqueue (x, tq, c);
                return;
            } 
            trgt++;
        }
    }
    printf ("o numero a ser adicionado não foi declarado como parte de um time");
    return;
}


void dequeue (teamqueue *q){
    if (q->first == NULL) return;

    element *atual = q->first->first;
    printf("%d\n", atual->id);
    q->first->first = atual->next;
    free(atual);

    if (q->first->first == NULL) {  // se a fila interna ficou vazia, removemos o time
        internalteam *tmp = q->first;
        q->first = q->first->nextonq;
        free(tmp);
    }
}

int main() {
    int nteam, caso = 1;
    while (scanf("%d", &nteam) && nteam != 0) {
        printf("Scenario #%d\n", caso++);
        
        teamqueue fila;
        fila.first = NULL;
        
        memset(elist, 0, sizeof(elist)); // Limpa a hash table

        for (int i = 1; i <= nteam; i++) {
            int qnt, membro;
            scanf("%d", &qnt);
            for (int j = 0; j < qnt; j++) { // Lê os times e mapeia os membros
                scanf("%d", &membro);
                element temp;
                temp.id = membro;
                botanotime(i, temp, elist); // associa o membro ao time i
            }
        }

        char comando[20];
        while (scanf("%s", comando) && strcmp(comando, "STOP") != 0) {
            if (strcmp(comando, "ENQUEUE") == 0) {
                int x;
                scanf("%d", &x);
                findteamenqueue(x, &fila, elist, nteam);
            } else if (strcmp(comando, "DEQUEUE") == 0) {
                dequeue(&fila);
            }
        }

        printf("\n");
    }

    return 0;
}