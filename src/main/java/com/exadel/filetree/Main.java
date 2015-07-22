package com.exadel.filetree;

import com.exadel.filetree.data.ChangeDescription;
import com.exadel.filetree.data.FileIndex;
import com.exadel.filetree.service.IService;
import com.exadel.filetree.service.ServiceException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.PrintStream;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: naXa!
 * Date: 14.06.13
 * Time: 15:07
 */
public class Main {
    static final PrintStream cons = System.out;

    /**
     * Entry point.
     *
     * @param args  program arguments.
     *              The 1st argument must be a path to a directory.
     **/
    public static void main(String args[]) {
        ApplicationContext context = new ClassPathXmlApplicationContext( "classpath:app-context.xml" );
        if (args.length < 1) {
            cons.println("[!] You must specify a folder as the first argument in order to run this program.\n" +
                            "Usage: %program_name% \"Drive:\\path\\to\\folder\"");
            return;
        }

        try {
            //IService srvc = (IService)FileTreeAppContext.getInstance().getBean( "useThis" );
            IService srvc = (IService)context.getBean( "useThis" );

            File targetDir = new File( args[0] );
            Set<FileIndex> state2 = srvc.describeIt( targetDir );
            if (!srvc.wasSerialized( targetDir )) {
                    // First run
                srvc.saveDescription( state2, targetDir );
                cons.println("[i] OK! I've remembered directory \"" + args[0] + "\".");
            } else {
                    // Successive runs
                Set<FileIndex> state1 = srvc.loadDescription( targetDir );

                cons.println("--- - ---\n Report \n--- - ---");
                Set<ChangeDescription> report = srvc.compareStates( state1, state2 );
                for (ChangeDescription cd : report)
                    cons.println( cd.toString() + ": " + cd.getFilename() );

                srvc.saveDescription( state2, targetDir );
            }
        } catch (ServiceException exc) {
            System.err.println( exc.getMessage() );
            exc.printStackTrace();
        }
    }
}
