#include <stdio.h>
#include <stdbool.h>

typedef struct node{
    char color; // R = rubra, N = negra
    int id;
    struct node *left, *right;
} node;

typedef struct{
    node *root;
} tree;

bool isRed(node *n){
    return (n != NULL && n->color == 'R');
}

void flipcolors(node *n){
    n->color = 'R';
    if (n->left != NULL){
        n->left->color = 'N';
    }
    if (n->right != NULL){
        n->right->color = 'N';
    }
}

node *rotateleft(node *a){
    node *b = a->right;
    a->right = b->left;
    b->left = a;
    b->color = a->color;
    a->color = 'R';
    return b;
}

node *rotateright(node *a){
    node *b = a->left;
    a->left = b->right;
    b->right = a;
    b->color = a->color;
    a->color = 'R';
    return b;
}

node *insere(int n, node *pai){
    if (pai == NULL){
        node *new = (node *) malloc(sizeof(node));
        new->id = n;
        new->left = NULL;
        new->right = NULL;
        new->color = 'R';
        return new;
    }
    if (n > pai->id){ //maior, insere pra direita
        pai->right = insere (n,pai->right);
    }
    else if (n < pai->id){ //menor, insere pra esquerda
        pai->left = insere (n,pai->left);
    }
    else{ //igual, não insere
        return pai;
    }

    if (isRed(pai->right) && !isRed(pai->left)){
        pai = rotateleft(pai);
    }
    if (isRed(pai->left) && isRed(pai->left->left)){
        pai = rotateright(pai);
    }
    if (isRed(pai->right) && isRed(pai->left)){
        flipcolors(pai);
    }

    return pai;
}

void printa (node*n){
    if(n != NULL ){
        printf("%d%c ", n->id, n->color);
        printa(n->left);
        printa(n->right);
    }
}

int main(){
    tree LLRB;
    LLRB.root = NULL;
    int x;

    while(scanf("%d", &x) == 1){
        LLRB.root = insere(x , LLRB.root);
        LLRB.root->color = 'N';
    }

    printa(LLRB.root);

    return 1;
}