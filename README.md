# OpenShift Cookbook - Chapter 5 Sample Application#

A simple Job portal written using Java EE 6 and PostgreSQL 9.2.

To run it on OpenShift, run the following command.

```
$ rhc app-create jobstore jbosseap postgresql-9.2 --from-code https://github.com/OpenShift-Cookbook/chapter5-jobstore-security.git
```

Also create following tables and insert couple of records as shown below.
```
CREATE TABLE USERS(email VARCHAR(64) PRIMARY KEY, password VARCHAR(64));
CREATE TABLE USER_ROLES(email VARCHAR(64), role VARCHAR(32));
INSERT into USERS values('admin@jobstore.com', 'ISMvKXpXpadDiUoOSoAfww==');
INSERT into USER_ROLES values('admin@jobstore.com', 'admin');
```
