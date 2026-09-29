public class Loteria{
    private int quantos;
    private Concurso c_concursos[];
    private Pessoa c_part[];
    
    public Loteria(){
        this.quantos = 0;
        c_concursos = new Concurso[51];
        c_part = new Pessoa[400];
    }
    
    int qpart = 0;
    
    public void cria_concurso(int id, int tam, int preço){
        if (c_concursos[id-1] == null){
            quantos++;
        }
        c_concursos[id-1] = new Concurso(id, tam, preço);
    }

    public void vender(Pessoa p){
        int qual = (int)(Math.random() * quantos);
        int part = 0;
        for (int acc = 0; acc <= qpart; acc++){
            if (c_part[acc] != null && p.id == c_part[acc].id){
                part = acc;
                break;
            }
        }
        if (c_part[part] == null){
            qpart++;
            c_part[part] = p;
            }
            
        if (c_part[part].bufunfa < c_concursos[qual].preço){
            System.out.print ("Dinheiro insuficiente para comprar, vai trabalhar.");
            return;
        }
        c_part[part].bufunfa-=c_concursos[qual].preço;
        
        c_part[part].bilhete[0][c_part[part].cont] = qual+1;
        
        for(int acc = 1; acc <= 3; acc++){
            c_part[part].bilhete[acc][c_part[part].cont] = (int)(Math.random() * c_concursos[qual].tam);
        }
        
         c_part[part].cont++;
        c_concursos[qual].lucro += c_concursos[qual].preço;
        
    }

    public void vender(Pessoa p, int concid){
        int part = 0;
        for (int acc = 0; acc <= qpart; acc++){
            if (c_part[acc] != null && p.id == c_part[acc].id){
                part = acc;
                break;
            }
        }
        if (c_part[part] == null){
            qpart++;
            c_part[part] = p;
            }
        
        if ( c_part[part].bufunfa < c_concursos[concid-1].preço){
            System.out.print ("Dinheiro insuficiente para comprar, vai trabalhar.");
            return;
        }
        
        c_part[part].bilhete[0][c_part[part].cont] = concid;
        
        c_part[part].bufunfa-=c_concursos[concid-1].preço;
        
        for(int acc = 1; acc <= 3; acc++){
            c_part[part].bilhete[acc][c_part[part].cont] = (int)(Math.random() * c_concursos[concid-1].tam);
        }
        c_part[part].cont++;
        c_concursos[concid-1].lucro += c_concursos[concid-1].preço;
    }

    public void vender(Pessoa p, int concid, int n1, int n2, int n3){
        int part = 0;
        for (int acc = 0; acc <= qpart; acc++){
            if (c_part[acc] != null){
                part = acc;
                break;
            }
        }
        if (c_part[part] == null){
            qpart++;
            c_part[part] = p;
            }
        
        if ( c_part[part].bufunfa < c_concursos[concid-1].preço){
            System.out.print ("Dinheiro insuficiente para comprar, vai trabalhar.");
            return;
        }
        c_part[part].bufunfa-=c_concursos[concid-1].preço;
        
        c_part[part].bilhete[0][c_part[part].cont] = concid;
        
        c_part[part].bilhete[1][c_part[part].cont] = n1;
        c_part[part].bilhete[2][c_part[part].cont] = n2; 
        c_part[part].bilhete[3][c_part[part].cont] = n3;
        
        c_part[part].cont++;
        c_concursos[concid-1].lucro += c_concursos[concid-1].preço;
    }

    public void vender(Pessoa p, int n1, int n2, int n3){
        int qual = (int)(Math.random() * quantos);
        int part = 0;
        for (int acc = 0; acc <= qpart; acc++){
            if (c_part[acc] != null && p.id == c_part[acc].id){
                part = acc;
                break;
            }
        }
        if (c_part[part] == null){
            qpart++;
            c_part[part] = p;
            }
        
        if (c_part[part].bufunfa < c_concursos[qual].preço){
            System.out.print ("Dinheiro insuficiente para comprar, vai trabalhar.");
            return;
        }
        c_part[part].bufunfa-=c_concursos[qual].preço;
        
        c_part[part].bilhete[0][c_part[part].cont] = qual+1;
        
        c_part[part].bilhete[1][c_part[part].cont] = n1;
        c_part[part].bilhete[2][c_part[part].cont] = n2; 
        c_part[part].bilhete[3][c_part[part].cont] = n3;
        
        c_part[part].cont++;
        c_concursos[qual].lucro += c_concursos[qual].preço;
    }

    public void sorteia_resultado(){
        int qtde_pessoas = 5;
        for (int conta = quantos-1; conta >= 0; conta--){
            int nvencedor = 0;
            int vencedores[] = new int[qtde_pessoas];
            for (int dim = qpart-1; dim>=0; dim--){
                for (int outro = 0; outro<c_part[dim].cont; outro++){
                   if(c_concursos[conta].id == c_part[dim].bilhete[0][outro]){
                       if (c_concursos[conta].sorteado == c_part[dim].bilhete[1][outro]
                       || c_concursos[conta].sorteado == c_part[dim].bilhete[2][outro]
                       || c_concursos[conta].sorteado == c_part[dim].bilhete[3][outro]){
                           vencedores[nvencedor] = c_part[dim].id;
                           nvencedor++;
                       }
                   }
                }
                
            }
            
            if (nvencedor==0){
                System.out.print("Não houve nenhum vencedor para o concurso " +(conta+1) +"\n");
            }
            else{
                double premio = (c_concursos[conta].lucro*0.7) / nvencedor;
                System.out.print ("Houve(ram) "+nvencedor+" vencedor(es) para o concurso "+(conta+1)
                +"! ele(s) foi (foram) a(s) pessoa(s) de número: ");
                for (int maisum = 0; maisum<nvencedor; maisum++){
                    System.out.print(+vencedores[maisum]+" ");
                    c_part[maisum].bufunfa += premio;
                }
                System.out.print("\n");
            }
        }
        
    }

}






