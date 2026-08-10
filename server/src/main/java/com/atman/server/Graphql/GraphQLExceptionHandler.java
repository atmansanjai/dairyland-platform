package com.atman.server.Graphql;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GraphQLExceptionHandler {

    @GraphQlExceptionHandler
    public GraphQLError handleIllegalArgumentException(IllegalArgumentException ex, DataFetchingEnvironment environment) {
        return GraphqlErrorBuilder.newError()
                                  .message(ex.getMessage())
                                  .path(environment.getExecutionStepInfo().getPath())
                                  .location(environment.getField().getSourceLocation())
                                  .errorType(ErrorType.BAD_REQUEST)
                                  .build();
    }

    @GraphQlExceptionHandler
    public GraphQLError handleDataIntegrityViolation(DataIntegrityViolationException ex, DataFetchingEnvironment environment) {
        String message = "Database error: A constraint was violated (e.g., value too long or duplicate entry).";

        String causeMessage = ex.getMostSpecificCause().getMessage();
        if (causeMessage != null) {
            if (causeMessage.contains("character varying(10)")) {
                message = "Contact number cannot exceed 10 characters.";
            } else if (causeMessage.contains("violates unique constraint")) {
                message = "A record with this unique value already exists.";
            } else if (causeMessage.contains("violates not-null constraint")) {
                message = "A required field is missing or null in the database.";
            }
        }

        return GraphqlErrorBuilder.newError()
                                  .message(message)
                                  .path(environment.getExecutionStepInfo().getPath())
                                  .location(environment.getField().getSourceLocation())
                                  .errorType(ErrorType.BAD_REQUEST)
                                  .build();
    }

    @GraphQlExceptionHandler
    public GraphQLError handleNullPointerException(NullPointerException ex, DataFetchingEnvironment environment) {
        return GraphqlErrorBuilder.newError()
                                  .message("An unexpected internal null value was encountered.")
                                  .path(environment.getExecutionStepInfo().getPath())
                                  .location(environment.getField().getSourceLocation())
                                  .errorType(ErrorType.INTERNAL_ERROR)
                                  .build();
    }

    @GraphQlExceptionHandler
    public GraphQLError handleRuntimeException(RuntimeException ex, DataFetchingEnvironment environment) {
        // Fallback for custom service exceptions like "Admin not found"
        String message = ex.getMessage() != null ? ex.getMessage() : "An unexpected error occurred.";

        return GraphqlErrorBuilder.newError()
                                  .message(message)
                                  .path(environment.getExecutionStepInfo().getPath())
                                  .location(environment.getField().getSourceLocation())
                                  .errorType(ErrorType.BAD_REQUEST)
                                  .build();
    }

    @GraphQlExceptionHandler
    public GraphQLError handleGenericException(Exception ex, DataFetchingEnvironment environment) {
        return GraphqlErrorBuilder.newError()
                                  .message("An internal server error occurred.")
                                  .path(environment.getExecutionStepInfo().getPath())
                                  .location(environment.getField().getSourceLocation())
                                  .errorType(ErrorType.INTERNAL_ERROR)
                                  .build();
    }
}