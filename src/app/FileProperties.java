package app;

public class FileProperties {
    private String name;
    private long size;
    private long compressedSize;
    private int compressedMethod;

    public FileProperties(String name, long size, long compressedSize, int compressedMethod) {
        this.name = name;
        this.size = size;
        this.compressedSize = compressedSize;
        this.compressedMethod = compressedMethod;
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public long getCompressedSize() {
        return compressedSize;
    }

    public int getCompressedMethod() {
        return compressedMethod;
    }

    public long getCompressedRatio() {
        return 100 - ((compressedSize * 100) / size);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).
                append(" file,").
                append(" original size: ").
                append(size / 1024).
                append(" Kb, compressed size: ").
                append(compressedSize / 1024).
                append(" Kb, compression: ").
                append(getCompressedRatio());
        return sb.toString();
    }
}
