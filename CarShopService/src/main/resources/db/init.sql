-- Create custom schema for entity tables
CREATE SCHEMA IF NOT EXISTS entity_schema;

-- Create service schema for service/system tables
CREATE SCHEMA IF NOT EXISTS service_schema;

-- Optionally, set search paths
ALTER ROLE root SET search_path TO entity_schema, service_schema, public;