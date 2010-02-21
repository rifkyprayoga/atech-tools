
         ***********************************************************
         ***                Translation Tool                     ***
         ***                  Version 0.1                        ***
         ***********************************************************
         

TABLE OF CONTENTS
=================

1. Introduction
2. Quick description of how application works
3. Quick How To
4. Description of different configuration




1. Introduction
================

(Simple) Translation Tool (in further text TT) is tool for translation of application files 
intended for internationalization of (java) applications. 


2. Quick description of how application works
===============================================

Application works by reading module configuration file (which is supplied by software 
vendor/developer), this file simply tells which module/application we are translating for. 
Next are so called master files, which are files that developers create, when making 
application (this are files containing mostly words to translate and also some special 
codes which enable us to read file and translate it). Both master file and translation 
file can also have configuration files, but this is used just internally (this file must 
not be deleted).

Internal directory structure:

    TT_Root (here you should copy correct (for operating system) startup batch files)
       |- bin (contains all startup batch files and TT, startup files won't work from here !!!)
       |- config (contains configurations for application and also user config files)
       |- docs (some documentation)
       |- files
            |- master_files (master files and master file configurations)
                    |- original
                         |-<version> (contains original master files for each langpack)
            |- translation (here are results of your work)
                    |- backup (automatic backup files)
            |- trans_original
                    |- <version>  (contains translation files for all supported languages)
                          ...


3. Quick How To
================

1. Now first you need to download TT package. This file contains atech-tools library, with
   working version of TT (0.4.3 or latest) and some startup files. (when application tool
   changes there will be new version of this file). Unpack this into some directory.
   
2. Secondly you need configuration files for application you are translation. This is so
   called Language Base Pack for certain application (<AppShortName>_Lang_Base_<version>.zip). 
   You will need to download this file just once, if not differently specified by developer.
   This will contain configuration and startup files for application with different of this 
   files (we will create this startup files for Windows, linux/Unix and Mac). Unpack this into
   same directories as file from step 1. You will get also some new documentation specific
   to application you are developing for. In this documentation you will also get list of files
   that are specific for different OS. 
   
3. Actual language files. This file (<AppShortName>_LangPack_<version_of_lang_pack>.zip. This 
   archive contains language files (master files and optional config files) and also latest 
   translations. You need to unpack to directory from step 1. New master files will overwrite 
   the old ones. Original (as supplied with application) translations will be stored into 
   files\trans_original\<lang_pack_version>. There you can also find list of currently supported
   languages. 
   
4. Now you need to modify (or copy from .config_default) TranslatorSettings.config file (config
   directory). There you set settings for you (as translator) and settings for languages you are
   translating. More on that in section 4.5.
    
5. Now you can start with work. On how you will deploy files that you translate, you need to 
   consult documentation from Lang_Base. Same goes for testing. Application needs to implement
   functionality, so that you can test files, without making changes to your applications .jar
   files.    
  


4. Description of different files
==========================================

4.1 module configuration file


4.2 master file


4.3 master configuration file


4.4 translation files (also config)


4.5 TranslatorSettings.config



