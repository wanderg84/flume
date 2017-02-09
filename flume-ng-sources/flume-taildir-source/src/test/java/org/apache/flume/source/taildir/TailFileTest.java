package org.apache.flume.source.taildir;

import junit.framework.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by 9230095 on 2017. 2. 9..
 */
public class TailFileTest {

    private File tmpDir;

    @Test
    public void testCreationTime() throws IOException {

        //given
        File f1 = new File(tmpDir, "file1");
        if (f1.exists()) f1.delete();

        f1.createNewFile();
        long inode = (long) Files.getAttribute(f1.toPath(), "unix:ino");

        //when
        TailFile tf = new TailFile(f1, new HashMap<String, String>(), inode, 0);

        //then
        FileTime fileTime = tf.getCreationTime();
        Assert.assertNotNull(fileTime);

        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Assert.assertEquals(df.format(new Date()), df.format(fileTime.toMillis()));

        f1.deleteOnExit();
    }

}