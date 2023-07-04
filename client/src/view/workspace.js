export const workspaceListItem = ({image, state, uuid}) => `
    <li>
        Image: <span style="color: #747bff">${image}</span>
        State: <span style="color: #C70039">${state}</span>
        <br/><a href="#${uuid}">Open</a>
    </li>
`;
