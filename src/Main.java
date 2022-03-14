import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static List<String> proxies;
    static List<String> liveProxies = new ArrayList<>();
    private static Socket socket;
    private static Thread thread;
    static int number = 0;

    public static void main(String[] args) throws IOException {
        System.out.println("Please input a file path");
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(System.in));
        File file = new File(streamReader.readLine());
        BufferedReader fileReader = new BufferedReader(new FileReader(file));

        proxies = fileReader.lines().toList();

        for (String proxy : proxies) {
            thread = new Thread(() -> {
                thread.setName("Thread " + number);
                try {
                    long currentTime = System.currentTimeMillis();
                    String address = proxy.split(":")[0];
                    String port = proxy.split(":")[1];
                    socket = new Socket(address, Integer.parseInt(port));
                    socket.close();
                    long endTime = System.currentTimeMillis() - currentTime;
                    System.out.println("Live proxy " + proxy + " " + (endTime) + " m/s ");
                    liveProxies.add(proxy);
                    Thread.sleep(150);
                } catch (Exception e) {
                    System.out.println("Dead " + proxy);
                }
            });
            thread.start();

            number++;
        }

        System.out.println("Location to store live proxies");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        File proxyFile = new File(bufferedReader.readLine());

        if (proxyFile.createNewFile()) {
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(proxyFile + ".txt"));
            for (String liveProxy : liveProxies) {
                fileWriter.write(liveProxy);
                fileWriter.newLine();
            }
            fileWriter.close();
        } else {
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(proxyFile + ".txt"));
            for (String liveProxy : liveProxies) {
                fileWriter.write(liveProxy);
                fileWriter.newLine();
            }
            fileWriter.close();
        }
    }
}

