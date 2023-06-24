export const workspaceView = ({image, state, uuid}) => `
    <li>
        Image: <span style="color: #747bff">${image}</span> 
        State: <span style="color: #747bff">${state}</span> 
        <br/><a href="#${uuid}">Open</a>
    </li>
`;
