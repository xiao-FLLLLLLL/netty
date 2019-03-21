public class NettyServer {
    public static void main(String[] args) {
        final int port = 2222;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new ImServer().run(port);
            }
        });
        thread.start();
    }
}
