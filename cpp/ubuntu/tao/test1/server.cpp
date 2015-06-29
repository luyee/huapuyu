#include <iostream>
#include "echoS.h"
using namespace std;

class Echo_i : public POA_Echo, public PortableServer::RefCountServantBase
{
public:

    inline Echo_i()
    {
    }

    virtual ~Echo_i()
    {
    }
    virtual char* echoString(const char* mesg);
};

char* Echo_i::echoString(const char* mesg)
{
    cerr << "Upcall " << mesg << endl;
    return CORBA::string_dup(mesg);
}

int main(int argc, char** argv)
{
    try
    {
        // initialize ORB
        CORBA::ORB_var orb = CORBA::ORB_init(argc, argv);
        // find RootPOA
        CORBA::Object_var obj = orb->resolve_initial_references("RootPOA");
        PortableServer::POA_var poa = PortableServer::POA::_narrow(obj);
        // create servant object and activate it
        Echo_i* myecho = new Echo_i();
        PortableServer::ObjectId_var myechoid = poa->activate_object(myecho);
        // Obtain a reference to the object, and print it out as a
        // stringified IOR.
        obj = myecho->_this();
        CORBA::String_var sior(orb->object_to_string(obj));
        cerr << (char*) sior << endl;
        myecho->_remove_ref();
        // find and activate POAManager
        PortableServer::POAManager_var pman = poa->the_POAManager();
        pman->activate();
        orb->run();
    }
    catch (CORBA::SystemException&)
    {
        cerr << "Caught CORBA::SystemException." << endl;
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
