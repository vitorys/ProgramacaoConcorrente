/*
    Programa em Java que use Threads para encontrar
    os ńnúmeros primos dentro de um intervalo.  O mé́etodo que
    contabiliza os ńnúmeros primos possui como entrada:
    valor inicial e final do intervalo, n ́umero de threads.
 */
package ThreadSafety;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import jdk.nashorn.internal.objects.NativeDate;

/**
 *
 * @author a1711199
 */
public class PrimosThreadSafety {

    private int maxNum;
    private int minNum;
    private int currentNum;
    private List<Integer> list = new ArrayList();

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public int getMinNum() {
        return minNum;
    }

    public void setMinNum(int minNum) {
        this.minNum = minNum;
    }

    public int getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(int currentNum) {
        this.currentNum = currentNum;
    }

    public PrimosThreadSafety() {
    }

    public static void main(String[] args) {
        PrimosThreadSafety primosThreadSafety = new PrimosThreadSafety();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite a quantidade de Threads a serem criadas:");
        int nThreads = scanner.nextInt();
        System.out.println("Digite o intervalo (número inicial)");
        primosThreadSafety.setMinNum(scanner.nextInt());
        System.out.println("Digite o intervalo (número final)");
        primosThreadSafety.setMaxNum(scanner.nextInt());

        primosThreadSafety.setCurrentNum(primosThreadSafety.getMinNum());

        primosThreadSafety.createNThreads(nThreads);

    }

    private void createNThreads(int numberOfThreads) {
        for (int i = 0; i < numberOfThreads; i++) {
            ThreadFactory thread = new ThreadFactory(this);
            thread.start();
        }
    }
}

class ThreadFactory extends Thread {

    PrimosThreadSafety primosThreadSafety = null;

    public ThreadFactory(PrimosThreadSafety primosThreadSafety) {
        this.primosThreadSafety = primosThreadSafety;
    }

    @Override
    public void run() {
        System.out.println("Running");
        int currentNumber = 0;

        synchronized (this) {
            currentNumber = primosThreadSafety.getCurrentNum();
            currentNumber = currentNumber + 1;
            primosThreadSafety.setCurrentNum(currentNumber);
        }

        while (currentNumber < primosThreadSafety.getMaxNum()) {

            int divisibleCount = 0;

            for (int i = 1; i <= currentNumber; i++) {
                if (currentNumber % i == 0) {
                    divisibleCount++;
                }
            }

            if (divisibleCount <= 2) {
                System.out.println("[Thread " + Thread.currentThread().getId() + "] Número " + currentNumber + " é primo!");
                primosThreadSafety.getList().add(currentNumber);
            }

            synchronized (this) {
                currentNumber = primosThreadSafety.getCurrentNum();
                currentNumber = currentNumber + 1;
                primosThreadSafety.setCurrentNum(currentNumber);
            }

        }
    }
}
