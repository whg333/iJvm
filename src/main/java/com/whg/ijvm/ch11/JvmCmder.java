package com.whg.ijvm.ch11;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.internal.Console;
import com.whg.ijvm.ch11.classfile.ClassFile;
import com.whg.ijvm.ch11.classpath.ClassData;
import com.whg.ijvm.ch11.classpath.Classpath;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public class JvmCmder {

	@Parameter(names = { "-help", "-?" }, description = "print help message")
	private boolean helpFlag;

	@Parameter(names = "-version", description = "print version and exit")
	private boolean versionFlag;

	@Parameter(names = { "-verbose:class", "-verbose" }, description = "enable verbose output")
	private boolean verboseClassFlag;

	@Parameter(names = "-verbose:inst", description = "enable verbose output")
	private boolean verboseInstFlag;

	@Parameter(names = { "-classpath", "-cp" }, description = "classpath")
	private String cpOption;
	
	@Parameter(names = "-Xjre", description = "path to jre")
	private String jreOption;

	private String className;
	private String[] args = new String[0];

	public void run(JCommander jCommander, String[] classArgs) {
		if (classArgs.length > 0) {
			className = classArgs[0];
			if(classArgs.length > 1){
				args = Arrays.copyOfRange(classArgs, 1, classArgs.length);
			}
		}

		if (versionFlag) {
			JCommander.getConsole().println("version 0.0.1");
		} else if (helpFlag || 
				(StringUtils.isEmpty(jreOption) && StringUtils.isEmpty(cpOption))) {
			jCommander.setProgramName("java -jar ijvm-jar-with-dependencies.jar");
			jCommander.usage();
		} else {
			startJVM();
		}
	}
	
	private void startJVM(){
		Classpath cp = new Classpath();
		cp.parse(jreOption, cpOption);
		
		Console console = JCommander.getConsole();
		console.println(String.format("classpath:%s\nclass:[%s]\nargs:%s", cp, className, Arrays.toString(args)));
		
		className = className.replaceAll("\\.", "/");
		ClassData classData = cp.readClass(className);
		if(classData == null){
			console.println(String.format("Can not found class:[%s]", className));
			return;
		}

		byte[] bytes = classData.bytes;
		String[] unsignedBytes = unsignedBytes(bytes);
		console.println(String.format("class data:%s", Arrays.toString(unsignedBytes)));

		ClassFile classFile = ClassFile.parse(bytes);
		console.println(classFile.getPrintInfo());

		new Jvm(this, cp).start();
	}

	private String[] unsignedBytes(byte[] bytes){
		String[] uints = new String[bytes.length];
		for(int i=0;i<bytes.length;i++){
			String hex = Integer.toHexString(Byte.toUnsignedInt(bytes[i])).toUpperCase();
			uints[i] = hex.length() == 1 ? "0"+hex : hex;
		}
		return uints;
	}

	public boolean versionFlag() {
		return versionFlag;
	}

	public boolean verboseClassFlag() {
		return verboseClassFlag;
	}

	public String[] args(){
		return args;
	}

	public String className(){
		return className;
	}

}
