from django.conf.urls.defaults import *

# Uncomment the next two lines to enable the admin:
from django.contrib import admin
from shh import views
admin.autodiscover()

urlpatterns = patterns('',
    # Example:
    # (r'^rems/', include('rems.foo.urls')),

    # Uncomment the admin/doc line below to enable admin documentation:
    # (r'^admin/doc/', include('django.contrib.admindocs.urls')),

    # Uncomment the next line to enable the admin:
    (r'^admin/', include(admin.site.urls)),
    (r'^hello/$', views.hello),
    (r'^hello/(\d+)/$', views.helloParam),
)
