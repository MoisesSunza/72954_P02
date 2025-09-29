import java.util.Random;

public class MonteCarloHilos {

    private static int globalCount = 0;
    private static final Object lock = new Object();

    static class Worker extends Thread {
        private final int numSamples;
        private final int threadId;

        public Worker(int numSamples, int threadId) {
            this.numSamples = numSamples;
            this.threadId = threadId;
        }

        @Override
        public void run() {
            Random random = new Random();
            int localCount = 0;

            for (int i = 0; i < numSamples; i++) {
                double x = random.nextDouble();
                double y = random.nextDouble();
                if (x * x + y * y <= 1.0) {
                    localCount++;
                }
            }

            synchronized (lock) {
                System.out.println("Hilo " + threadId + ": añadiendo " + localCount + " puntos al total.");
                globalCount += localCount;
            }
        }
    }

    public static void main(String[] args) {
        int totalSamples = 1000000;
        int numThreads = 4;
        int samplesPerThread = totalSamples / numThreads;

        Thread[] threads = new Thread[numThreads];

        long tiempoInicio = System.currentTimeMillis();

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Worker(samplesPerThread, i);
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long tiempoFinal = System.currentTimeMillis();

        double piApprox = 4.0 * globalCount / totalSamples;

        System.out.println("\nNúmero total de puntos: " + totalSamples);
        System.out.println("Puntos dentro del círculo: " + globalCount);
        System.out.println("Aproximación de pi: " + piApprox);
        System.out.println("Error: " + Math.abs(piApprox - Math.PI));
        System.out.println("Tiempo total de cálculo: " + (tiempoFinal - tiempoInicio) + " ms");
    }
}
