public class Principal {
public static void main(String[] args) {

    // cada aposta é composta por 3 números
    Loteria l1 = new Loteria();
    
    // números de 1 a 50
    l1.cria_concurso( 1, 50, 3 );
    
    // números de 1 a 40
    l1.cria_concurso( 2, 40, 4 );
    
    int qtde_pessoas = 5;
    
    Pessoa[] c_pessoas = new Pessoa[ qtde_pessoas ];
    
    // id e quantia em dinheiro
    c_pessoas[ 0 ] = new Pessoa( 101, 100 );
    c_pessoas[ 1 ] = new Pessoa( 102, 50 );
    c_pessoas[ 2 ] = new Pessoa( 103, 10 );
    
    // a pessoa só consegue comprar se tem dinheiro suficiente
    // em caso de compra, os saldos são atualizados
    // pessoa1 faz uma aposta escolhendo o concurso e os números
    l1.vender( c_pessoas[ 0 ], 1, 3, 6, 10);
    
    // pessoa1 faz uma aposta escolhendo o concurso,
    // mas os números da aposta não
    l1.vender( c_pessoas[ 0 ], 2);
    
    // pessoa2 compra bilhete escolhendo os números mas não o concurso
    l1.vender( c_pessoas[ 1 ], 3, 6, 10 );
    
    // pessoa2 não escolhe nem o concurso nem os números
    
    l1.vender( c_pessoas[ 1 ]);
    
    // loteria1 sorteia e imprime resultado
    // pode ter mais de um ganhador
    // 70% do arrecadado é dividido entre os ganhadores
    
    l1.sorteia_resultado();
    }
    }