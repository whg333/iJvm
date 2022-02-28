package com.whg.ijvm.ch06;

import java.io.File;
import java.util.Arrays;

import com.whg.ijvm.ch06.classfile.MemberInfo;
import com.whg.ijvm.ch06.classpath.ClassData;
import com.whg.ijvm.ch06.heap.RClass;
import com.whg.ijvm.ch06.heap.RClassLoader;
import com.whg.ijvm.ch06.heap.RMethod;
import com.whg.ijvm.ch06.instruction.Interpreter;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.internal.Console;
import com.whg.ijvm.ch06.classpath.Classpath;
import com.whg.ijvm.ch06.classfile.ClassFile;

public class JvmCmder {

	@Parameter(names = { "-help", "-?" }, description = "print help message")
	private boolean helpFlag;

	@Parameter(names = "-version", description = "print version and exit")
	private boolean versionFlag;

	@Parameter(names = { "-classpath", "-cp" }, description = "classpath")
	private String cpOption = "";
	
	@Parameter(names = "-Xjre", description = "path to jre")
	private String jreOption = "";

	private String clazz;
	private String[] args;

	public void run(JCommander jCommander, String[] args) {
		if (args.length > 2) {
			args = Arrays.copyOfRange(args, 2, args.length);
			this.clazz = args[0];
			this.args = Arrays.copyOfRange(args, 1, args.length);
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
		
		String info = String.format("classpath:%s class:%s args:%s", 
				cp, clazz, Arrays.toString(args));
		Console console = JCommander.getConsole();
		console.println(info);
		
		clazz = clazz.replaceAll("\\.", "/");
		// clazz = RegExUtils.replaceAll(clazz, "/.", "////");
		ClassData classData = cp.readClass(clazz);
		byte[] bytes = classData.bytes;
		if(bytes == null){
			info = String.format("Can not find or load main class:%s", clazz);
		}else{
			String[] unsignedBytes = unsignedBytes(bytes);
			info = String.format("class data:%s", Arrays.toString(unsignedBytes));
		}
		console.println(info);

		if(bytes != null){
			ClassFile classFile = ClassFile.parse(bytes);
			classFile.printInfo();

			RClassLoader classLoader = new RClassLoader(cp);
			RClass mainClass = classLoader.loadClass(clazz);
			RMethod mainMethod = mainClass.getMainMethod();
			// MemberInfo mainMethod = classFile.getMainMethod();
			if(mainMethod != null){
				Interpreter.run(mainMethod);
			}else{
				console.println(String.format("Main method not found in class %s\n", clazz));
			}
		}
	}
	
	private String[] unsignedBytes(byte[] bytes){
		String[] uints = new String[bytes.length];
		for(int i=0;i<bytes.length;i++){
			String hex = Integer.toHexString(Byte.toUnsignedInt(bytes[i])).toUpperCase();
			uints[i] = hex.length() == 1 ? "0"+hex : hex;
		}
		return uints;
	}

}
