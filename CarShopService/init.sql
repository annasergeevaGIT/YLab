-- Create schemas
CREATE SCHEMA IF NOT EXISTS service_schema;
CREATE SCHEMA IF NOT EXISTS entity_schema;

-- Grant permissions (if needed)
GRANT USAGE ON SCHEMA service_schema TO root;
GRANT USAGE ON SCHEMA entity_schema TO root;