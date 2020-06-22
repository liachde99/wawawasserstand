package de.wasserwacht.wasserstand.python;

import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.FactoryBean;

public class PythonServiceFactory implements FactoryBean<PythonService>{

	@Override
	public PythonService getObject() throws Exception {
		//The python classpath is usually set by environment variable 
		//or included in the java project classpath but it can also be set
		// programmatically.  Here I hard code it just for the example.
			//This is not required if we use jython standalone JAR 
			PySystemState systemState = Py.getSystemState();
			systemState.path.append(new PyString("C:\\jython2.7.2\\Lib"));		
		 
			//Here is the actual code that interprets our python file. 
			PythonInterpreter interpreter = new PythonInterpreter();
			interpreter.execfile("python\\PythonServiceP.py"); 
			PyObject buildingObject = interpreter.get("PythonServiceP").__call__(); 
		 
		//Cast the created object to our Java interface 
			return (PythonService) buildingObject.__tojava__(PythonService.class);
	}

	@Override
	public Class<?> getObjectType() {
		return PythonService.class;
	}

}
