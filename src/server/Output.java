package server;

import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.Color.*;

import java.awt.Color;

import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class Output {

	public static boolean isGUI = false;

	public static void log(MessageStyle style, String format, Object... args) {
		final StringBuilder sb = new StringBuilder(String.format(format, args));
		if (isGUI) {
			appendStyledString(style, sb.toString());
		} else {
			Ansi ansi = ansiFromStyle(style);
			ansi.a(sb.toString());
			System.out.print(ansi);
		}
	}

	static void appendStyledString(final MessageStyle style, final String s) {

		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				appendStyledStringMain(style, s);
			}
		});
	}

	static void appendStyledStringMain(MessageStyle style, String s) {
		/*
		 * AttributeSet aset = attrsFromStyle(style);
		 * 
		 * int len = jTextPane1.getDocument().getLength(); // same value as //
		 * getText().length(); jTextPane1.setCaretPosition(len); // place caret
		 * at the end (with no // selection) StyledDocument doc =
		 * jTextPane1.getStyledDocument(); try { doc.insertString(len, s, aset);
		 * } catch (BadLocationException e) { } // there is no selection, so
		 * inserts at caret
		 */
	}

	public static void print(MessageStyle style, String s) {
		log(style, s);
	}

	public static void println(MessageStyle style, String s) {
		print(style, s + "\n");
	}

	public static void print(String s) {
		log(MessageStyle.Normal, s);
	}

	public static void println(String s) {
		print(s + "\n");
	}
	
	public static void println() {
		print("\n");
	}
	
	public static void printf(MessageStyle style, String format, Object... args) {
		 log(style, format, args);
	}
	
	public static void printf(String format, Object... args) {
		 log(MessageStyle.Normal, format, args);
	}

	public static enum MessageStyle {
		Normal, Important, Error, Warning, Trace, Success
	}

	static Ansi ansiFromStyle(MessageStyle s) {
		Ansi ansi = new Ansi();
		ansi.boldOff().reset();
		switch (s) {
		case Error:
			ansi = ansi.bold().fg(RED);
			break;
		case Warning:
			ansi = ansi.boldOff().fg(RED);
			break;
		case Trace:
			ansi = ansi.bold().fg(BLACK);
			break;
		case Normal:
			ansi = ansi.boldOff().fg(WHITE);
			break;
		case Important:
			ansi = ansi.bold().fg(WHITE);;
			break;
		case Success:
			ansi = ansi.boldOff().fg(GREEN);
			break;
		}
		return ansi;
	}

	static AttributeSet attrsFromStyle(MessageStyle s) {
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = SimpleAttributeSet.EMPTY;

		Color c = null;
		switch (s) {
		case Error:
			c = Color.red;
			break;
		case Warning:
			c = Color.pink;
			break;
		case Trace:
			c = Color.lightGray;
			break;
		case Normal:
			c = Color.white;
			break;
		case Important:
			c = Color.white;
			aset = sc.addAttribute(aset, StyleConstants.Bold, true);
			break;
		case Success:
			c = Color.green; // dark green-ish
			aset = sc.addAttribute(aset, StyleConstants.Bold, true);
			break;
		}
		aset = sc.addAttribute(aset, StyleConstants.Foreground, c);

		return aset;
	}

}
