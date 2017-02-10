#include <iostream>
#include "echoC.h"
using namespace std;

static void hello(Echo_ptr e)
{
    CORBA::String_var src = (const char*) "Hello!";
    CORBA::String_var dest = e->echoString(src);
    cerr << "I said, \"" << (char*) src << "\"." << endl
         << "The Echo object replied, \"" << (char*) dest << "\"." << endl;
}

int main(int argc, char* argv[])
{
    try
    {
        // initialize orb
        CORBA::ORB_var orb = CORBA::ORB_init(argc, argv);
        // check arguments
        if (argc != 2)
        {
            cerr << "Usage: eg1_cli <object reference>" << endl;
            throw 1;
        }
        // Obtain reference from servant IOR
        CORBA::Object_var obj = orb->string_to_object(argv[1]);
        Echo_var echoref = Echo::_narrow(obj);
        if (CORBA::is_nil(echoref))
        {
            cerr << "Can't narrow reference to type Echo (or it was nil)." << endl;
            return 1;
        }
        for (CORBA::ULong count = 0; count < 10; count++)
            // communicate with servant
            hello(echoref);
        orb->destroy();
    }
    catch (CORBA::COMM_FAILURE&)
    {
        cerr << "Caught system exception COMM_FAILURE -- unable to contact the "
                << "object." << endl;
    }
    catch (CORBA::SystemException&)
    {
        cerr << "Caught a CORBA::SystemException." << endl;
    }
    catch (CORBA::Exception&)
    {
        cerr << "Caught CORBA::Exception." << endl;
    }
    catch (...)
    {
        cerr << "Caught unknown exception." << endl;
    }

    return 0;
}
