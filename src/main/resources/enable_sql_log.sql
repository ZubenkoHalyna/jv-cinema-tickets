SET GLOBAL general_log = 1;

SHOW GLOBAL VARIABLES WHERE Variable_name in ('version', 'log', 'general_log', 'general_log_file', 'log_output');