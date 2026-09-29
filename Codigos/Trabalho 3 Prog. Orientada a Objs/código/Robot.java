
// Alunos: Ana Lu Tan (124035468) & Arthur Faria Azevedo (124051228)

public class Robot extends Item{
    public Robot(){
        super();
        this.pos_line = 1;
        this.pos_column = 1;
        this.paredeAux = direcaoAux.DIR;
        this.status = statusRobo.INICIO;
    }

    private direcaoAux paredeAux;
    private int semilaps;
    private statusRobo status;
    private int linha_antes = 0;
    private int coluna_antes = 0;
    private boolean teste = false; 


    public enum direcaoAux{
        DIR, ESQ, CIMA, BAIXO;
    }

    public enum statusRobo{
        INICIO, CIRCUITO, ACHAR_OBSTACULO
    }

    private Move direcaoMovimento(){          //metodo que diz para onde o robo deve andar com base de onde esta a parede
        if(paredeAux == direcaoAux.DIR){
            return Move.DOWN;
        }
        if(paredeAux == direcaoAux.ESQ){
            return Move.UP;
        }
        if(paredeAux == direcaoAux.CIMA){
            return Move.RIGHT;
        }
        else{
            return Move.LEFT;
        }
    }

    private Move Testemao (){          //metodo que teste se ele está encostando na parede
        if(paredeAux == direcaoAux.DIR){
            return Move.RIGHT;
        }
        if(paredeAux == direcaoAux.ESQ){
            return Move.LEFT;
        }
        if(paredeAux == direcaoAux.CIMA){
            return Move.UP;
        }
        else{
            return Move.DOWN;
        }
    }

    //se robo nao consegue andar, muda a parede "em que ele esta apoiando" e a direcao em que ele deve andar
    private void mudarDirecao(){
        if(paredeAux == direcaoAux.CIMA){
            paredeAux = direcaoAux.DIR;

        } else if(paredeAux == direcaoAux.BAIXO){
            paredeAux = direcaoAux.ESQ;

        } else if(paredeAux == direcaoAux.ESQ){
            paredeAux = direcaoAux.CIMA;

        } else if(paredeAux == direcaoAux.DIR){
            paredeAux = direcaoAux.BAIXO;

        }
    }

    private void mudarMao(){
        if(paredeAux == direcaoAux.CIMA){
            paredeAux = direcaoAux.ESQ;

        } else if(paredeAux == direcaoAux.BAIXO){
            paredeAux = direcaoAux.DIR;

        } else if(paredeAux == direcaoAux.ESQ){
            paredeAux = direcaoAux.BAIXO;

        } else if(paredeAux == direcaoAux.DIR){
            paredeAux = direcaoAux.CIMA;

        }
    }


    public Move move(){
        int chegada_l = this.mat1.getQttyLines()/2;

        int linha_atual = mat1.get_line(this.id);
        int coluna_atual = mat1.get_column(this.id);

        if(linha_atual == chegada_l){
            this.semilaps++;

            if(semilaps == 10){
                return Move.STOP;
            }
        }

        // inicio da corrida: robo indo ate a linha de chegada
        if(status == statusRobo.INICIO){
            if(linha_atual < chegada_l){
                return Move.DOWN;
            }
		    
            if(linha_atual == chegada_l){     //robo chegou na linha de chegada. agora vai tentar achar obstaculo
                status = statusRobo.ACHAR_OBSTACULO;
            }
        }

        //robo indo para a direita até achar o obstaculo
        if(status == statusRobo.ACHAR_OBSTACULO){
            if(coluna_atual != coluna_antes){
                linha_antes = linha_atual;
                coluna_antes = coluna_atual;
                return Move.RIGHT;
            } else{
                linha_antes = 1;
                coluna_antes = 1;
                status = statusRobo.CIRCUITO;
            }
        }

        //robo esta colado no obstaculo e ele vai contorna-lo realizando o circuito

        if(status == statusRobo.CIRCUITO){
            Move movimento = direcaoMovimento();
            if (teste == true){
                if(coluna_antes == coluna_atual && linha_antes == linha_atual){
                    mudarDirecao();
                    movimento = direcaoMovimento();

                    linha_antes = linha_atual;
                    coluna_antes = coluna_atual;

                    teste = false;

                    return movimento;
                }
                movimento = Testemao();
                
                teste = false;

                linha_antes = linha_atual;
                coluna_antes = coluna_atual;

                return movimento;
            }

            if (coluna_antes != coluna_atual || linha_antes != linha_atual){
                    mudarMao();
                    movimento = direcaoMovimento();
                }

                teste = true;
                linha_antes = linha_atual;
                coluna_antes = coluna_atual;

                return movimento;
            }

     return Move.STOP;
    }
}

