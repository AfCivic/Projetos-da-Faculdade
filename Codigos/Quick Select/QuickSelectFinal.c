
#include <stdio.h>
#include <stdlib.h>

int QuickSelect(int list[], int trg, int bg, int end);

int main(int argc, char *argv[])
{
int max = 100;
int *Lista = (int *) malloc(max *sizeof(int));
int c, n = 0;
int resultado;
int k = atoi(argv[2]);

    while(n<4){
        scanf("%i", &c); //pega os numeros do teclado
        n++;
        Lista[n - 1] = c; //coloca os numeros na lista conforme eles são digitados
        if (n>=max){
            max*=2;
            int *temp = (int *) realloc(Lista, max *sizeof(int)); //"realoca o vetor caso ele seja muito grande"
           Lista = temp;
        }
    }
    
    resultado = QuickSelect(Lista, 2, 0, n-1);

    printf ("%d", resultado);
}

int QuickSelect(int list[], int trg, int bg, int end){
    int xd = rand() % (end - bg) + bg; //gera um numero aleatorio entre o começo e o fim
    int bb = list[xd]; //pega o valor da casa selecionada
    int membg = bg, memend = end; //lembra dos valores originais do começo e do fim
    int temp; //valor temporario
    int bool; // valor booleano
    int result;
    while (bg < end){
        if (list[bg] < list[xd]){
            bg++;
        }
        else {
            bool = 1;
            while (bool){
                if (list[end] >= list[xd]){
                    end--;
                }
                else{
                    temp = list [end];
                    list[end] = list[bg];
                    list[bg] = temp;
                    bool = 0;
                }
            }
        }
    }
    if (bg == trg-1){
        return bb;
    }
    if (trg < bg){
        result = QuickSelect(list, trg, membg, end);
    }
        if (trg > bg){
        result = QuickSelect(list, trg, bg, memend);
    }
    return result;
    //meu codigo ta em duas linguas pq eu tenho problemas
}