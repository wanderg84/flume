/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

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