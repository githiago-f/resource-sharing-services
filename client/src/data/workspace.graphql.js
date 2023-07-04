import { queries as query } from './queries.graphql.js';
import { graphQlUrl } from './config.js';


/**
 *
 * @param {'homePage' | 'createWorkspace'} operationName
 * @param {unknown extends Object} vars
 * @returns {Promise<any>}
*/
export const graphql = (operationName, vars) => {
    const token = window.localStorage.getItem('token');
    const headers = new Headers();
    headers.append('Content-Type', 'application/json');
    headers.append('Authorization', `Bearer ${token}`);
    return fetch(graphQlUrl(), {
        body: JSON.stringify({
            query, operationName, variables: vars
        }),
        method: 'POST',
        headers,
    })
        .then(res => res.json())
        .then(res=>{
            if(res.errors) {
                console.error(res.errors);
            }
            return res.data;
        });
}
