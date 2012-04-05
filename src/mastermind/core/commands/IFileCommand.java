package mastermind.core.commands;

import java.io.IOException;
import org.apache.commons.io.FileExistsException;

/**
 * 
 * @author Andrew Church
 * 
 *         This interface provides similar methods to ICommand, but specific to
 *         file handling. These execute/undo methods are capable of throwing
 *         IOException, and FileExistsException
 */
public interface IFileCommand {

	void execute() throws IOException, FileExistsException;

	void undo() throws IOException, FileExistsException;
}
