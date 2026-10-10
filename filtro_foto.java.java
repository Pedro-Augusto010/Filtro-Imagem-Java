import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/*
 * Resposta da atividade:
 * A interface aparece em Filtro, que define o metodo aplicar(). A heranca
 * aparece nas classes FiltroCinza e FiltroNegativo, que estendem a classe
 * abstrata FiltroPixel. O polimorfismo ocorre quando a variavel do tipo
 * Filtro recebe um objeto FiltroCinza ou FiltroNegativo e chama aplicar(),
 * executando o filtro escolhido pelo usuario.
 */

interface Filtro {
    BufferedImage aplicar(BufferedImage original);
}

abstract class FiltroPixel implements Filtro {

    @Override
    public BufferedImage aplicar(BufferedImage original) {
        BufferedImage resultado = new BufferedImage(
            original.getWidth(),
            original.getHeight(),
            BufferedImage.TYPE_INT_ARGB
        );

        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < original.getWidth(); x++) {
                int pixel = original.getRGB(x, y);
                int novoPixel = transformarPixel(pixel);
                resultado.setRGB(x, y, novoPixel);
            }
        }

        return resultado;
    }

    protected abstract int transformarPixel(int pixel);
}

class FiltroCinza extends FiltroPixel {

    @Override
    protected int transformarPixel(int pixel) {
        int alpha = (pixel >>> 24) & 0xFF;
        int vermelho = (pixel >>> 16) & 0xFF;
        int verde = (pixel >>> 8) & 0xFF;
        int azul = pixel & 0xFF;

        int cinza = (vermelho + verde + azul) / 3;

        return (alpha << 24) | (cinza << 16) | (cinza << 8) | cinza;
    }
}

class FiltroNegativo extends FiltroPixel {

    @Override
    protected int transformarPixel(int pixel) {
        int alpha = (pixel >>> 24) & 0xFF;
        int vermelho = (pixel >>> 16) & 0xFF;
        int verde = (pixel >>> 8) & 0xFF;
        int azul = pixel & 0xFF;

        vermelho = 255 - vermelho;
        verde = 255 - verde;
        azul = 255 - azul;

        return (alpha << 24) | (vermelho << 16) | (verde << 8) | azul;
    }
}

class Principal {

    public static void main(String[] args) {
        try {
            BufferedImage original = carregarImagem("imagem.png");
            Scanner entrada = new Scanner(System.in);

            System.out.println("Escolha o filtro:");
            System.out.println("1 - Tons de cinza");
            System.out.println("2 - Negativo");

            if (!entrada.hasNextInt()) {
                System.out.println("Opcao invalida.");
                return;
            }

            int opcao = entrada.nextInt();
            Filtro filtro;

            if (opcao == 1) {
                filtro = new FiltroCinza();
            } else if (opcao == 2) {
                filtro = new FiltroNegativo();
            } else {
                System.out.println("Opcao invalida.");
                return;
            }

            BufferedImage resultado = filtro.aplicar(original);
            salvarImagem(resultado, "resultado.png");
            exibirImagens(original, resultado);
            System.out.println("Resultado salvo em resultado.png");

        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public static BufferedImage carregarImagem(String caminho) throws IOException {
        BufferedImage imagem = ImageIO.read(new File(caminho));
        if (imagem == null) {
            throw new IOException("Formato de imagem nao reconhecido.");
        }
        return imagem;
    }

    public static void salvarImagem(BufferedImage imagem, String caminho) throws IOException {
        boolean salvou = ImageIO.write(imagem, "png", new File(caminho));
        if (!salvou) {
            throw new IOException("Nao foi possivel salvar no formato PNG.");
        }
    }

    public static BufferedImage copiarImagem(BufferedImage original) {
        BufferedImage copia = new BufferedImage(
            original.getWidth(),
            original.getHeight(),
            BufferedImage.TYPE_INT_ARGB
        );

        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < original.getWidth(); x++) {
                copia.setRGB(x, y, original.getRGB(x, y));
            }
        }

        return copia;
    }

    public static void exibirImagens(BufferedImage original, BufferedImage resultado) {
        SwingUtilities.invokeLater(() -> {
            JFrame janela = new JFrame("Comparacao de imagens");
            janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            janela.setLayout(new GridLayout(1, 2, 10, 0));

            janela.add(criarPainel("Original", original));
            janela.add(criarPainel("Resultado", resultado));

            janela.setSize(1000, 600);
            janela.setLocationRelativeTo(null);
            janela.setVisible(true);
        });
    }

    private static JPanel criarPainel(String titulo, BufferedImage imagem) {
        JPanel painel = new JPanel(new java.awt.BorderLayout());
        JLabel rotuloTitulo = new JLabel(titulo, SwingConstants.CENTER);
        JLabel rotuloImagem = new JLabel(new ImageIcon(imagem));

        painel.add(rotuloTitulo, java.awt.BorderLayout.NORTH);
        painel.add(new JScrollPane(rotuloImagem), java.awt.BorderLayout.CENTER);

        return painel;
    }
}
