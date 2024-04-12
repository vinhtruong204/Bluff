package sound;

public interface SoundStatus {
    // start
    public void start();

    // loop
    public void loop(int i);

    // stop
    public void stop();

    // resume
    public void resume();

    // close
    public void close();
}
