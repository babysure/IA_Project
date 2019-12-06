package com.lmax.api.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Writer that will roll files as they exceed a certain size.
 */
public class RollingFileWriter extends Writer
{
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private final String pattern;
    private final int maxSize;
    private final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-HHmmssSSS");
    private final File directory;

    private Writer writer = null;
    private int size = 0;

    /**
     * Create a Rolling file writer with a specified directory, filename pattern and max size.
     *
     * @param directory The directory to store the files.
     * @param pattern   The pattern used to create the file name, must contain a '%s' as it is used in a string format call.
     * @param maxSize   The maximum size for the individual file in bytes.
     */
    public RollingFileWriter(File directory, String pattern, int maxSize)
    {
        if (!pattern.contains("%s"))
        {
            throw new IllegalArgumentException("Pattern " + pattern + " must contain %s somewhere");
        }

        if (maxSize < 512)
        {
            throw new IllegalArgumentException("Max size: " + maxSize + " must be greater than 512");
        }

        if (directory.isFile())
        {
            throw new IllegalArgumentException("Can not create a directory called " + directory.getAbsolutePath() + ", it's a file");
        }

        if (!directory.exists() && !directory.mkdirs())
        {
            throw new IllegalArgumentException("Unable to create directory: " + directory.getAbsolutePath());
        }

        this.directory = directory;
        this.pattern = pattern;
        this.maxSize = maxSize;
    }

    /**
     * Writes a portion of an array of characters.
     *
     * @param cbuf Array of characters
     * @param off  Offset from which to start writing characters
     * @param len  Number of characters to write
     * @throws IOException If an I/O error occurs
     */
    @Override
    public void write(char[] cbuf, int off, int len) throws IOException
    {
        if (writer == null)
        {
            String filename = String.format(pattern, format.format(new Date()));
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(directory, filename)), UTF_8));
        }

        writer.write(cbuf, off, len);
        size += len;
    }

    /**
     * Doesn't flush the underlying stream, but will check if the file sufficiently
     * large that it needs to be rolled over to the next file.
     *
     * @throws IOException If an I/O error occurs
     */
    @Override
    public void flush() throws IOException
    {
        if (size >= maxSize)
        {
            try
            {
                writer.close();
            }
            finally
            {
                writer = null;
                size = 0;
            }
        }
    }

    /**
     * Closes the stream, flushing it first. Once the stream has been closed,
     * further write() or flush() invocations will cause an IOException to be
     * thrown. Closing a previously closed stream has no effect.
     *
     * @throws IOException If an I/O error occurs
     */
    @Override
    public void close() throws IOException
    {
        writer.close();
    }
}
