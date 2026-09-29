import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Principal {

    private JFrame janela2;
    private JFrame janela;
    private JTextField caixaTexto, caixaTexto2;
    private JLabel text;
    private JButton botaoEnviar;

        public void achadanger(int i, int j, Celula[][] campo, JButton[][] celula){
            if (!celula[i][j].isEnabled()) {
                return;
            }
            int danger = 0;
            for (int ilado = i-1; ilado <=i+1; ilado++){
                for (int jlado = j-1; jlado <=j+1; jlado++){
                    if (ilado >= 0 && ilado < campo.length && jlado >= 0 && jlado < campo[0].length){
                        if (campo[ilado][jlado].perdeu()){
                        danger++;
                        }
                    }
                }
            }
            celula[i][j].setEnabled(false);
            celula[i][j].setBackground(Color.WHITE);
            if (danger > 0) {
                celula[i][j].setText(String.valueOf(danger));
            }
            if (danger == 0){
                for (int ilado = i-1; ilado <=i+1; ilado++){
                    for (int jlado = j-1; jlado <=j+1; jlado++){
                        if (ilado >= 0 && ilado < campo.length && jlado >= 0 && jlado < campo[0].length) {
                            achadanger(ilado, jlado, campo, celula);
                        }
                    }
                }
            }
        }

        public void mostraminas(Celula[][] campo, JButton[][] celula){
            int i = campo.length;
            int j = campo[0].length;
            for (int cont = 0; cont<i; cont++){
                    for (int cont2 = 0; cont2<j; cont2++){
                        if (campo[cont][cont2].perdeu()){
                            celula[cont][cont2].setText("X");
                        }
                }
            }
        }

        public void desmostraminas(Celula[][] campo, JButton[][] celula){
            int i = campo.length;
            int j = campo[0].length;
            for (int cont = 0; cont<i; cont++){
                    for (int cont2 = 0; cont2<j; cont2++){
                            if (campo[cont][cont2].perdeu() && celula[cont][cont2].isEnabled()) {
                            celula[cont][cont2].setText("");
                        }
                }
            }

        }

    public Principal() {
        janela = new JFrame("Campo Minado");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setSize(400, 150);
        janela.setLayout(new FlowLayout());

        text = new JLabel("Escolha as dimensões (entre 3 e 15) do campo >:D :   ");
        text.setLayout(new FlowLayout());
        caixaTexto = new JTextField(2);
        caixaTexto2 = new JTextField(2);
        botaoEnviar = new JButton("Começar");

        janela.add(text);
        janela.add(caixaTexto);
        janela.add(caixaTexto2);
        janela.add(botaoEnviar);
        janela.setVisible(true);
       

        botaoEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obter o texto da caixa de texto
                String i_texto = caixaTexto.getText();
                String j_texto = caixaTexto2.getText();

                // Converter as dimensões para um inteiro
                int i;
                    i = Integer.parseInt(i_texto);
                if (i>15||i<3) {
                    // Tratamento do erro se a entrada não for um número válido
                    System.out.println("Erro: Valor inválido de i");
                    JOptionPane.showMessageDialog(botaoEnviar, "Erro: Valor inválido de i");
                    return;
                }

                int j;
                    j = Integer.parseInt(j_texto);
                if (j>15||j<3){
                    // Tratamento do erro se a entrada não for um número válido, de novo
                    System.out.println("Erro: Valor inválido de j");
                    JOptionPane.showMessageDialog(botaoEnviar, "Erro: Valor inválido de j");
                    return;
                }

                // Atribuir os valores às variáveis
                System.out.println("i: " + i);
                System.out.println("j: " + j);

                janela.setVisible(false);

                GridLayout jogo = new GridLayout(i+1,j+1);

                JPanel painel = new JPanel(jogo);


                janela2 = new JFrame("Campo Minado");
                janela2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                janela2.setSize(i*60, (j+1)*60);
                janela2.setLayout(new BorderLayout());
                Random gerador = new Random();

                Celula[][] campo;
                campo = new Celula[i][j];

                for (int cont = 0; cont<i; cont++){
                    for (int cont2 = 0; cont2<j; cont2++){
                    campo[cont][cont2] = new Celula();
                    }
                }
                final int[] minas = {0};
                for (int cont = 0, minacont = 0, minamax = i*j, qtdminas = minamax/5; cont<i; cont++){
                    for (int cont2 = 0; cont2<j; cont2++){
                        int rand = gerador.nextInt(minacont,minamax);
                        if (rand <= qtdminas){
                            campo[cont][cont2].setmina();
                            minacont++;
                            minas[0]++;
                        }
                        else{
                            minamax--;
                        }
                    }
                }

                JButton[][] celula = new JButton[i][j];
                final int[] condvitoria = {0};

                for (int cont = 0; cont<i; cont++){
                    for (int cont2 = 0; cont2<j; cont2++){
                        int cordi = cont;
                        int cordj = cont2;
                        celula[cordi][cordj] = new JButton();
                        celula[cordi][cordj].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                        if (campo[cordi][cordj].perdeu()){
                            celula[cordi][cordj].setBackground(Color.WHITE);
                            celula[cordi][cordj].setEnabled(false); 
                            JOptionPane.showMessageDialog( celula[1][1],"Perdeu otario!!!!!");
                            mostraminas(campo, celula);
                            janela.setVisible(true);
                        }
                        else{
                           achadanger(cordi,cordj,campo,celula);
                           condvitoria[0]++;
                           if (condvitoria[0] == (i*j) - minas[0]){
                            JOptionPane.showMessageDialog( celula[1][1],"Bom Trabalho!!!");
                            janela.setVisible(true);
                           }
                        }
                        }
                        });
                        
                        painel.add(celula[cordi][cordj]);
                    }
                }

                JButton revela = new JButton("Revelar as minas");

                revela.addActionListener(new ActionListener() {
                    boolean modo = true;
                    @Override
                 public void actionPerformed(ActionEvent e) {
                    if (modo){
                        mostraminas(campo, celula);
                        revela.setText("Revelar as minas");
                    }
                    else{
                        desmostraminas(campo, celula);
                        revela.setText("Esconder as minas");
                    }
                    modo = !modo;
                }
                
                });
                
                janela2.add(painel, BorderLayout.CENTER);
                janela2.add(revela, BorderLayout.SOUTH);
                janela2.setVisible(true);
                
            }
        });
    }
    
public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Principal());
    }

}
