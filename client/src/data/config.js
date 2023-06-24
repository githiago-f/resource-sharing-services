const baseURL = 'http://192.168.0.112:8081';
const graphQl = baseURL + '/graphql';

export const serviceUrl = () => new URL(baseURL);
export const graphQlUrl = () => new URL(graphQl);
