package com.xbrother.common.utils;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xbrother.common.exception.BizsException;
import com.xbrother.common.exception.enums.UtilsExceptionCode;

/**
 * Description:
 * 
 * @author Arian Zhang
 * @email hzhang@digitnexus.com
 * @Date 2013-7-22 上午11:44:46
 * @since v1.0.0
 */
public class StreamUtils {

	private final static Logger LOGGER = LoggerFactory.getLogger(StreamUtils.class);

	/**
	 * Write output stream from input stream.
	 * 
	 * @param inputStream
	 * @param outputStream
	 */
	public static void writeStream(InputStream inputStream, OutputStream outputStream) {
		if (!(outputStream instanceof BufferedOutputStream)) {
			outputStream = new BufferedOutputStream(outputStream);
		}
		try {
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			outputStream.flush();
		} catch (IOException e) {
			throw new BizsException(e, UtilsExceptionCode.IO_EXCEPTION);
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				LOGGER.error("close output stream occur error!", e);
			}
		}
	}

	public static void writeFile(File file, InputStream inputStream) {
		file.getParentFile().mkdirs();
		try {
			if (file.createNewFile()) {
				FileOutputStream fileOutputStream = new FileOutputStream(file);
				writeStream(inputStream, fileOutputStream);
			}
		} catch (IOException e) {
			throw new BizsException(e, UtilsExceptionCode.IO_EXCEPTION);
		}
	}

	public static void closeStream(InputStream inputStream, OutputStream outputStream) {
		closeInputStream(inputStream);
		closeOutputStream(outputStream);
	}

	public static void closeInputStream(InputStream inputStream) {
		if (null != inputStream) {
			try {
				inputStream.close();
			} catch (IOException e) {
				LOGGER.error("", e);
			}
		}
	}

	public static void closeOutputStream(OutputStream outputStream) {
		if (null != outputStream) {
			try {
				outputStream.close();
			} catch (IOException e) {
				LOGGER.error("", e);
			}
		}
	}

	public static void closeReaderAndWriter(Reader reader, Writer writer) {
		closeReader(reader);
		closeWriter(writer);
	}

	public static void closeReader(Reader reader) {
		if (null != reader) {
			try {
				reader.close();
			} catch (IOException e) {
				LOGGER.error("", e);
			}
		}
	}

	public static void closeWriter(Writer writer) {
		if (null != writer) {
			try {
				writer.close();
			} catch (IOException e) {
				LOGGER.error("", e);
			}
		}
	}

	public static void closeable(Closeable closeable) {
		if (null != closeable) {
			try {
				closeable.close();
			} catch (IOException e) {
				LOGGER.error("", e);
			}
		}
	}

	public static void closeSocket(Socket socket) {
		if (null != socket) {
			try {
				socket.close();
			} catch (IOException e) {
				LOGGER.error("", e);
			}
		}
	}
}
