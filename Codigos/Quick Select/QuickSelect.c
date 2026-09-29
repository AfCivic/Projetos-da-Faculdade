
#include <stdio.h>
#include <stdlib.h>

int main()
{
int QuickSelect(int list[], int trg, int bg, int end);
int Lista[100];
int c, n = 0;
int resultado;

    while(n<8){
        n += scanf("%i", &c); //pega os numeros do teclado
        printf ("%d %d \n", n, c);
        Lista[n - 1] = c; //coloca os numeros na lista conforme eles são digitados
    }
    
    for(int cont=0;cont<=n;cont++){
        printf("%d",Lista[cont]);
    }
    printf("\n");
    resultado = QuickSelect(Lista, 6, 0, n-1);

    printf ("o resultado eh %d", resultado);
}

int QuickSelect(int list[], int trg, int bg, int end){
    int xd = rand() % (end - bg) + bg; //gera um numero aleatorio entre o começo e o fim
    printf ("%d ", xd);
    printf ("%d :", list[xd]);
    printf ("%d ", bg);
    printf ("%d : ", end);
    int bb = list[xd]; //pega o valor da casa selecionada
    int membg = bg, memend = end; //lembra dos valores originais do começo e do fim
    int temp; //valor temporario
    int bool; // valor booleano
    while (bg < end){
        if (list[bg] < list[xd]){
            printf ("%d,", bg);
            printf ("%d |", list[bg]);
            bg++;
        }
        else {
            bool = 1;
            while (bool){
                if (list[end] >= list[xd]){
                    printf ("%d.", end);
                    printf ("%d |", list[end]);
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
    printf ("saiu do while ");
    if (bg == trg-1){
        return bb;
    }
    if (trg < bg){
        temp = list[xd];
        list[xd] = list[bg];
        list[bg] = temp;
        QuickSelect(list, trg, membg, end);
    }
        if (trg > bg){
        temp = list[xd];
        list[xd] = list[end];
        list[end] = temp;
        QuickSelect(list, trg, bg, memend);
    }
    
}