package com.lilithsthrone.modding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map.Entry;

public class PluginHashes {
    private static final byte VERSION = 1;

    HashMap<String, byte[]> hashes = new HashMap<>();
    Path pluginHashesPath = Path.of("data", "pluginhashes.dat");

    public PluginHashes() {

    }

    public void load() {
        File file = pluginHashesPath.toFile();
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(file);
            FileChannel fc = stream.getChannel();
            MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            if (bb.get() == VERSION) {
                for (int i = 0; i < bb.getInt(); i++) {
                    String modID = this.decodeString(bb);
                    byte[] hash = this.getByteArray(bb);
                    this.hashes.put(modID, hash);
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (stream != null)
                    stream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void save() {
        File file = pluginHashesPath.toFile();
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file);
            MappedByteBuffer bb = stream.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, file.length());
            bb.put((byte) 1); // Version
            bb.putInt(hashes.size());
            for (Entry<String, byte[]> kvp : hashes.entrySet()) {
                this.putUTF8String(bb, kvp.getKey());
                this.putByteArray(bb, kvp.getValue());
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (stream != null)
                    stream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void putUTF8String(MappedByteBuffer bb, String key) {
        this.putByteArray(bb, StandardCharsets.UTF_8.encode(key).array());
    }

    private void putByteArray(MappedByteBuffer bb, byte[] data) {
        bb.putInt(data.length);
        bb.put(data);
    }

    private String decodeString(MappedByteBuffer bb) {
        return new String(this.getByteArray(bb), StandardCharsets.UTF_8);
    }

    private byte[] getByteArray(MappedByteBuffer bb) {
        int sz = bb.getInt();
        byte[] buf = new byte[sz];
        bb.get(buf);
        return buf;
    }

}
