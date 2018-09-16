# Design Document

Some initial thoughts on how some of the CLI commands could be implemented.

Clarifying some terminologies...
* nobox - a box that doesn't exist. A user creates a nobox.
* nobox hub - a cloud storage directory configured to connect other nobox
  instances
* nobox instance - a directory on a user's local file system configured to be
  mirroring a nobox

## Some potential problems
* Compared to git (or even Dropbox), directory to index may be much larger
  * git repository - usually below 1 GB
  * Dropbox - usually below 5 GB, potentially up to 50 GB
  * nobox - an entire hard-drive (> 100 GB)

## local config file - `.nobox/config.yml`

One created for each directory in a local file system that wants to mirror a
nobox. This could be used to store:
* location of remote
* name of local

Other notes:
* chose to use yml so it can be manually configured

## Index - `.nobox/index`

Store a bunch of "tree" objects which act like a key-value store - name: hash.
The hash is either a pointer to another tree, or hash of a file in that
directory.

## remote state file - `.nobox/files.json`

One created at each remote (cloud storage). This could be used to store:
* file/directory structure and meta data (tree)

Other notes:
* maybe we don't need to use anything human readable like json.

## Simple use-cases

### Initial setup

1. `nobox create-instance` - indexes all files, includes prompts to set hub url
2. `nobox push` - push index and files to hub

### Additional instance

1. `nobox create-instance {HUB_URL}` - create a nobox instance
2. `nobox pull` - pull available files

### Adding files

1. `nobox refresh .` - reindexes current directory
2. `nobox hub` - make sure there's no conflicts
3. `nobox push` - push index and files to hub

## CLI - `nobox create-instance`

Index local directory

1. Parse `.nobox/config.yml`
2. Read `.nobox/index/`

## CLI - `nobox refresh`

Re-index local directory, save it and spit out the diff

1. Parse `.nobox/config.yml`
2. Read `.nobox/index`
3. Hash every file in directory and compare with the index
4. If there's a diff, save new hashes to index

## CLI - `nobox hub`

Check for any diff between instance vs. hub.

1. Parse `.nobox/config.yml`
2. Read `.nobox/index`
3. Read `remote:.nobox/index`
4. Compare two indexes and spit out diff

## Plan of action

1. Implement a way to create the index for a local path.

