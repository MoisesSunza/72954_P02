import java.util.Random;

public class MonteCarloJava {

    public static void main(String[] args) {
        int totalSamples = 1000000;
        int circulo = 0;

        Random random = new Random();

        long tiempoInicio = System.currentTimeMillis();

        for (int i = 0; i < totalSamples; i++) {
            double x = random.nextDouble(); // número en [0,1)
            double y = random.nextDouble(); // número en [0,1)

            if (x * x + y * y <= 1.0) {
                circulo++;
            }
        }

        long tiempoFinal = System.currentTimeMillis();

        double piApprox = 4.0 * circulo / totalSamples;

        System.out.println("Número total de puntos: " + totalSamples);
        System.out.println("Puntos dentro del círculo: " + circulo);
        System.out.println("Aproximación de pi: " + piApprox);
        System.out.println("Error: " + Math.abs(piApprox - Math.PI));
        System.out.println("Tiempo total de cálculo: " + (tiempoFinal - tiempoInicio) + " ms");
    }
}
