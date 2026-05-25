import java.util.Scanner;

public class CriptografiaMatriz {

    //conversão caractere pra numero
    static int charParaNumero(char c) {
        if (c == ' ') return 0;
        return Character.toUpperCase(c) - 'A' + 1;
    }
    //conversão numero pra caractere
    static char numeroParaChar(int n) {
        if (n == 0) return ' ';
        return (char) ('A' + n - 1);
    }

    //converte string pra uma matriz 3x3
    static int[][] stringParaMatriz(String s) {
        int[][] m = new int[3][3];

        while (s.length() < 9) {
            s += " ";
        }

        int k = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                m[i][j] = charParaNumero(s.charAt(k));
                k++;
            }
        }
        return m;
    }

    //converte a matriz pra uma string 
    static String matrizParaString(int[][] m) {
        String resultado = "";

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                resultado += numeroParaChar(m[i][j]);
            }
        }
        return resultado;
    }

    //multiplica as matrizes
    static int[][] multiplicar(int[][] A, int[][] B) {
        int[][] R = new int[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                R[i][j] = 0;
                for (int k = 0; k < 3; k++) {
                    R[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return R;
    }

    //Calcula determinante
    static int determinante(int[][] m) {
        return m[0][0]*(m[1][1]*m[2][2] - m[1][2]*m[2][1])
             - m[0][1]*(m[1][0]*m[2][2] - m[1][2]*m[2][0])
             + m[0][2]*(m[1][0]*m[2][1] - m[1][1]*m[2][0]);
    }

    //Calcula a matriz inversa
    static double[][] inversa(int[][] m) {
        double[][] inv = new double[3][3];
        int det = determinante(m);

        if (det == 0) {
            System.out.println("Matriz nao invertivel");
            return null;
        }

        inv[0][0] =  (m[1][1]*m[2][2] - m[1][2]*m[2][1]);
        inv[0][1] = -(m[0][1]*m[2][2] - m[0][2]*m[2][1]);
        inv[0][2] =  (m[0][1]*m[1][2] - m[0][2]*m[1][1]);

        inv[1][0] = -(m[1][0]*m[2][2] - m[1][2]*m[2][0]);
        inv[1][1] =  (m[0][0]*m[2][2] - m[0][2]*m[2][0]);
        inv[1][2] = -(m[0][0]*m[1][2] - m[0][2]*m[1][0]);

        inv[2][0] =  (m[1][0]*m[2][1] - m[1][1]*m[2][0]);
        inv[2][1] = -(m[0][0]*m[2][1] - m[0][1]*m[2][0]);
        inv[2][2] =  (m[0][0]*m[1][1] - m[0][1]*m[1][0]);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                inv[i][j] /= det;
            }
        }

        return inv;
    }

    //multiplica pela inversa
    static int[][] multiplicarComInversa(int[][] A, double[][] B) {
        int[][] R = new int[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                double soma = 0;
                for (int k = 0; k < 3; k++) {
                    soma += A[i][k] * B[k][j];
                }
                R[i][j] = (int) Math.round(soma);
            }
        }
        return R;
    }

    //le a matriz
    static int[][] lerMatriz(Scanner sc) {
        int[][] m = new int[3][3];


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                while (!sc.hasNextInt()) {
                    sc.next(); // descarta invalido
                }
                m[i][j] = sc.nextInt();
            }
        }

        return m;
    }

    //print da matriz
    static void mostrar(int[][] m) {
        for (int i = 0; i < 3; i++) {
            System.out.print("[ ");
            for (int j = 0; j < 3; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println("]");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int op;
        System.out.println("1 - Codificar");
        System.out.println("2 - Decodificar");
        op = sc.nextInt();
        sc.nextLine(); // buffer

        if (op == 1) {
            System.out.print("Digite a mensagem (max 9 chars): ");
            String msg = sc.nextLine();

            if (msg.length() > 9) {
                msg = msg.substring(0, 9);
            }

            int[][] M = stringParaMatriz(msg);

            System.out.println("Digite a matriz chave:");
            int[][] K = lerMatriz(sc);

            int[][] C = multiplicar(M, K);

            System.out.println("Mensagem codificada:");
            mostrar(C);

        } else if (op == 2) {
            System.out.println("Digite a matriz codificada:");
            int[][] C = lerMatriz(sc);

            System.out.println("Digite a matriz chave:");
            int[][] K = lerMatriz(sc);

            double[][] inv = inversa(K);

            if (inv != null) {
                int[][] M = multiplicarComInversa(C, inv);

                String msg = matrizParaString(M);

                System.out.println("Mensagem decodificada:");
                System.out.println(msg);
            }
        }

        sc.close();
    }
}


/*
    MATRIZ CHAVE DE EXEMPLO:
    1 0 1
    1 3 1
    0 1 1

    Pra usar uma matriz como chave, ela tem que ser invertível
    (Honestamente, não sei como saber se uma matriz é invertível.)
*/