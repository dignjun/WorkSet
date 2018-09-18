package ex03.pyrmont.connector.util;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 这个是
 * @author DINGJUN
 *
 */
@SuppressWarnings("unused")
public class SocketInputStream extends InputStream {

	private static final byte CR = 13;
	private static final byte LF = 10;
	private static final byte SP = 32;
	private static final byte HT = 9;
	private static final byte COLON = 58;
	private static final int LC_OFFSET = -32;
	protected byte[] buf;
	protected int count;
	protected int pos;
	protected InputStream is;
	protected static StringManager sm = StringManager
			.getManager("ex03.pyrmont.connector.http");

	public SocketInputStream(InputStream is, int bufferSize) {
		this.is = is;
		this.buf = new byte[bufferSize];
	}

	public void readRequestLine(HttpRequestLine requestLine) throws IOException {
		if (requestLine.methodEnd != 0) {
			requestLine.recycle();
		}
		int chr = 0;
		do {
			try {
				chr = read();
			} catch (Exception e) {
				chr = -1;
			}
		} while ((chr == 13) || (chr == 10));
		if (chr == -1) {
			throw new EOFException(sm.getString("requestStream.readline.error"));
		}
		this.pos -= 1;
		int maxRead = requestLine.method.length;
		int readStart = this.pos;
		int readCount = 0;

		boolean space = false;

		while (!space) {
			if (readCount >= maxRead) {
				if (2 * maxRead <= 1024) {
					char[] newBuffer = new char[2 * maxRead];
					System.arraycopy(requestLine.method, 0, newBuffer, 0,
							maxRead);

					requestLine.method = newBuffer;
					maxRead = requestLine.method.length;
				} else {
					throw new IOException(
							sm.getString("requestStream.readline.toolong"));
				}

			}

			if (this.pos >= this.count) {
				int val = read();
				if (val == -1) {
					throw new IOException(
							sm.getString("requestStream.readline.error"));
				}

				this.pos = 0;
				readStart = 0;
			}
			if (this.buf[this.pos] == 32) {
				space = true;
			}
			requestLine.method[readCount] = (char) this.buf[this.pos];
			readCount++;
			this.pos += 1;
		}

		requestLine.methodEnd = (readCount - 1);

		maxRead = requestLine.uri.length;
		readStart = this.pos;
		readCount = 0;

		space = false;

		boolean eol = false;

		while (!space) {
			if (readCount >= maxRead) {
				if (2 * maxRead <= 32768) {
					char[] newBuffer = new char[2 * maxRead];
					System.arraycopy(requestLine.uri, 0, newBuffer, 0, maxRead);

					requestLine.uri = newBuffer;
					maxRead = requestLine.uri.length;
				} else {
					throw new IOException(
							sm.getString("requestStream.readline.toolong"));
				}

			}

			if (this.pos >= this.count) {
				int val = read();
				if (val == -1) {
					throw new IOException(
							sm.getString("requestStream.readline.error"));
				}
				this.pos = 0;
				readStart = 0;
			}
			if (this.buf[this.pos] == 32) {
				space = true;
			} else if ((this.buf[this.pos] == 13) || (this.buf[this.pos] == 10)) {
				eol = true;
				space = true;
			}
			requestLine.uri[readCount] = (char) this.buf[this.pos];
			readCount++;
			this.pos += 1;
		}

		requestLine.uriEnd = (readCount - 1);

		maxRead = requestLine.protocol.length;
		readStart = this.pos;
		readCount = 0;

		while (!eol) {
			if (readCount >= maxRead) {
				if (2 * maxRead <= 1024) {
					char[] newBuffer = new char[2 * maxRead];
					System.arraycopy(requestLine.protocol, 0, newBuffer, 0,
							maxRead);

					requestLine.protocol = newBuffer;
					maxRead = requestLine.protocol.length;
				} else {
					throw new IOException(
							sm.getString("requestStream.readline.toolong"));
				}

			}

			if (this.pos >= this.count) {
				int val = read();
				if (val == -1) {
					throw new IOException(
							sm.getString("requestStream.readline.error"));
				}
				this.pos = 0;
				readStart = 0;
			}
			if (this.buf[this.pos] != 13) {
				if (this.buf[this.pos] == 10) {
					eol = true;
				} else {
					requestLine.protocol[readCount] = (char) this.buf[this.pos];
					readCount++;
				}
			}
			this.pos += 1;
		}

		requestLine.protocolEnd = readCount;
	}

	public void readHeader(HttpHeader header) throws IOException {
		if (header.nameEnd != 0) {
			header.recycle();
		}

		int chr = read();
		if ((chr == 13) || (chr == 10)) {
			if (chr == 13)
				read();
			header.nameEnd = 0;
			header.valueEnd = 0;
			return;
		}
		this.pos -= 1;

		int maxRead = header.name.length;
		int readStart = this.pos;
		int readCount = 0;

		boolean colon = false;

		while (!colon) {
			if (readCount >= maxRead) {
				if (2 * maxRead <= 128) {
					char[] newBuffer = new char[2 * maxRead];
					System.arraycopy(header.name, 0, newBuffer, 0, maxRead);
					header.name = newBuffer;
					maxRead = header.name.length;
				} else {
					throw new IOException(
							sm.getString("requestStream.readline.toolong"));
				}

			}

			if (this.pos >= this.count) {
				int val = read();
				if (val == -1) {
					throw new IOException(
							sm.getString("requestStream.readline.error"));
				}

				this.pos = 0;
				readStart = 0;
			}
			if (this.buf[this.pos] == 58) {
				colon = true;
			}
			char val = (char) this.buf[this.pos];
			if ((val >= 'A') && (val <= 'Z')) {
				val = (char) (val - '￠');
			}
			header.name[readCount] = val;
			readCount++;
			this.pos += 1;
		}

		header.nameEnd = (readCount - 1);

		maxRead = header.value.length;
		readStart = this.pos;
		readCount = 0;

		int crPos = -2;

		boolean eol = false;
		boolean validLine = true;

		while (validLine) {
			boolean space = true;

			while (space) {
				if (this.pos >= this.count) {
					int val = read();
					if (val == -1) {
						throw new IOException(
								sm.getString("requestStream.readline.error"));
					}
					this.pos = 0;
					readStart = 0;
				}
				if ((this.buf[this.pos] == 32) || (this.buf[this.pos] == 9)) {
					this.pos += 1;
					continue;
				}
				space = false;
			}

			while (!eol) {
				if (readCount >= maxRead) {
					if (2 * maxRead <= 4096) {
						char[] newBuffer = new char[2 * maxRead];
						System.arraycopy(header.value, 0, newBuffer, 0, maxRead);

						header.value = newBuffer;
						maxRead = header.value.length;
					} else {
						throw new IOException(
								sm.getString("requestStream.readline.toolong"));
					}

				}

				if (this.pos >= this.count) {
					int val = read();
					if (val == -1) {
						throw new IOException(
								sm.getString("requestStream.readline.error"));
					}
					this.pos = 0;
					readStart = 0;
				}
				if (this.buf[this.pos] != 13)
					if (this.buf[this.pos] == 10) {
						eol = true;
					} else {
						int ch = this.buf[this.pos] & 0xFF;
						header.value[readCount] = (char) ch;
						readCount++;
					}
				this.pos += 1;
			}

			int nextChr = read();

			if ((nextChr != 32) && (nextChr != 9)) {
				this.pos -= 1;
				validLine = false;
			} else {
				eol = false;

				if (readCount >= maxRead) {
					if (2 * maxRead <= 4096) {
						char[] newBuffer = new char[2 * maxRead];
						System.arraycopy(header.value, 0, newBuffer, 0, maxRead);

						header.value = newBuffer;
						maxRead = header.value.length;
					} else {
						throw new IOException(
								sm.getString("requestStream.readline.toolong"));
					}
				}

				header.value[readCount] = ' ';
				readCount++;
			}

		}

		header.valueEnd = readCount;
	}

	public int read() throws IOException {
		if (this.pos >= this.count) {
			fill();
			if (this.pos >= this.count)
				return -1;
		}
		return this.buf[(this.pos++)] & 0xFF;
	}

	public int available() throws IOException {
		return this.count - this.pos + this.is.available();
	}

	public void close() throws IOException {
		if (this.is == null)
			return;
		this.is.close();
		this.is = null;
		this.buf = null;
	}

	protected void fill() throws IOException {
		this.pos = 0;
		this.count = 0;
		int nRead = this.is.read(this.buf, 0, this.buf.length);
		if (nRead > 0)
			this.count = nRead;
	}
}
