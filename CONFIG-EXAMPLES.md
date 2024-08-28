# in code
${marathon.url:http://atrcxb2560-1.athtem.eei.ericsson.se:7070/v2}

# application.yml
marathon.url: from_application_yml


# from environment variable
MARATHON_URL=from_system_property

# from system property
-Dmarathon.url=from_system_property

# from command line argument
--marathon.url=from_system_property

# docker usage example
docker run -it --env MARATHON_URL=from_system_property armdocker.rnd.ericsson.se/aia/aia-ui
docker run -it armdocker.rnd.ericsson.se/aia/aia-ui --marathon.url=from_system_property


