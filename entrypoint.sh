#!/bin/bash
set -e

# Execute all SQL files in the /docker-entrypoint-initdb.d/ directory
for f in /docker-entrypoint-initdb.d/*.sql; do
    echo "Running $f..."
    psql -U $POSTGRES_USER -d $POSTGRES_DB -f "$f"
done

exec "$@"
