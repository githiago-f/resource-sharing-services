export const queries = 
'query homePage($page: Int) {' +
    'workspacesByUser(page: $page) {' + 
        'image, state, uuid }' + 
'} '+ 
'mutation createWorkspace($image: String, $params: String) {' + 
    'workspace(command: { image: $image, parameters: $params }) {' + 
        ' state, image, parameters, uuid }' + 
'}';