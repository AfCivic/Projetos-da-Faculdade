#include <stdio.h>

int cnj[100] = {0};
int rank[100] = {0};

int main(){
    int vert, arest;
    int cum = 0;
    scanf ("%d %d", &vert, &arest);

    for (int i = 1; i <= vert; i++) {
    cnj[i] = i;
    rank[i] = 0;
    }


    for (int cont = 1 ; cont < arest ; cont ++){
        int a, b, tam;
        scanf ("%d %d %d", &a, &b, &tam);
        cum += une (a, b, tam); 
    }

    printf ("%d", cum);

    return 0;

}


int find (int x){
    
    int y = x;
    
    while( x != cnj[x]){
        x = cnj[x];
    }
    
    int pai;
    
    while(y != x){
        pai = cnj[y];
        cnj[y] = x;
        y = pai;
    }
    
    return x;
    
}

int une (int a, int b, int tam){
    a = find(a);
    b = find(b);
    if (a == b){
        return 0;
    } 
    if(rank[a] < rank[b]){
        
        cnj[a] = b;
        rank[b]++;
        
        return tam;
        
    }else{
        
        cnj[b] = a;
        rank[a]++;
        
        return tam;
        
    }
    return 0;
}